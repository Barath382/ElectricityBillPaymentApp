package in.customer.electricitypayment.service;

import in.customer.electricitypayment.dto.PaymentRequestDTO;
import in.customer.electricitypayment.model.CreditDebitCard;
import in.customer.electricitypayment.model.Customer;
import in.customer.electricitypayment.model.ElectricityBill;
import in.customer.electricitypayment.model.PaymentTransaction;
import in.customer.electricitypayment.repository.CreditDebitCardRepository;
import in.customer.electricitypayment.repository.CustomerRepository;
import in.customer.electricitypayment.repository.ElectricityBillRepository;
import in.customer.electricitypayment.repository.PaymentTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PaymentService {


    private static final double DISCOUNT_BALANCE = 0.95;

    @Autowired
    private ElectricityBillRepository electricityBillRepository;

    @Autowired
    private CreditDebitCardRepository cardRepository;

    @Autowired
    private PaymentTransactionRepository transactionRepository;

    @Autowired
    private OTPService otpService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    EmailService emailService;


    public Map<String, List<String>> processPayment(PaymentRequestDTO paymentRequest) {

        Map<String, List<String>> generatedResponse = new HashMap<>();
        List<String> generatedTransactionId = new ArrayList<>();
        Optional<CreditDebitCard> optionalCard = cardRepository.findByCardNumberAndExpireDateAndCvv(paymentRequest.getCardNumber(), paymentRequest.getCardExpireDate(), paymentRequest.getCardCvv());
        for (Long billId : paymentRequest.getBillIds()) {
            ElectricityBill bill = electricityBillRepository.findById(billId)
                    .orElseThrow(() -> new RuntimeException("Bill not found with id " + billId));

            double amountAfterDiscount =  0.0;
            int discount = 0;
            LocalDate today = LocalDate.now();
            LocalDate dueDate = bill.getDueDate();
            boolean isBefore = dueDate.isBefore(today);
            if (!isBefore) {
                discount = 10;
                amountAfterDiscount = bill.getTotalAmount() * DISCOUNT_BALANCE * DISCOUNT_BALANCE;
            } else {
                discount = 5;
                amountAfterDiscount = bill.getTotalAmount() * DISCOUNT_BALANCE;
            }
            String message = "";

            PaymentTransaction transaction = new PaymentTransaction();
            transaction.setCardNumber(paymentRequest.getCardNumber());
            transaction.setTransactionId("TXN_" + System.currentTimeMillis());
            transaction.setCardExpireDate(paymentRequest.getCardExpireDate());
            transaction.setCardCvv(paymentRequest.getCardCvv());
            transaction.setTransactionDateTime(LocalDateTime.now());
            transaction.setBillId(bill);
            transaction.setTransactionAmount(amountAfterDiscount);
            transaction.setDiscount(discount);
            transaction.setTransactionMethod(PaymentTransaction.TransactionMethod.CARD);
            transaction.setTransactionStatus(PaymentTransaction.TransactionStatus.PROCESS);

            if (optionalCard.isEmpty()) {
                transaction.setBillId(bill);

                transaction.setTransactionStatus(PaymentTransaction.TransactionStatus.FAILED);
                transaction.setTransactionMessage("Invalid card details");
                transactionRepository.save(transaction);
                message = "Invalid card details";
                generatedResponse.put("message", List.of(message));
                return generatedResponse;
            }

            CreditDebitCard card = optionalCard.get();
            System.out.println(card.getBalance());
            System.out.println(paymentRequest.getPayAmount());
            if (card.getBalance() < paymentRequest.getPayAmount()) {
                transaction.setBillId(bill);
                transaction.setTransactionStatus(PaymentTransaction.TransactionStatus.FAILED);
                transaction.setTransactionMessage("Insufficient balance");
                transactionRepository.save(transaction);
                message = "Insufficient balance";
                generatedResponse.put("message", List.of(message));
                return generatedResponse;
            }
            generatedTransactionId.add(transaction.getTransactionId());


            transactionRepository.save(transaction);
        }

        CreditDebitCard card = optionalCard.get();
        String otp = otpService.generateOTPForPayment(card.getCardNumber(),card.getEmail());
        generatedResponse.put("transaction", generatedTransactionId);
        return generatedResponse;
    }


    public String  processWalletPayment(PaymentRequestDTO paymentRequest, String customerId) {



        Optional<Customer> customerOptional = customerRepository.findByCustomerId(customerId);
        String message;
        if (customerOptional.isEmpty()) {
            return "Customer not found";
        }

        Customer customer = customerOptional.get();

        if (paymentRequest.getPayAmount() > customer.getWallet()) {
            return "Insufficient Balance in Wallet";
        }
        else {
            for (Long billId : paymentRequest.getBillIds()) {
                ElectricityBill bill = electricityBillRepository.findById(billId)
                        .orElseThrow(() -> new RuntimeException("Bill not found with id " + billId));

                double amountAfterDiscount = 0.0;
                int discount = 0;
                LocalDate today = LocalDate.now();
                LocalDate dueDate = bill.getDueDate();
                boolean isBefore = dueDate.isBefore(today);
                if (!isBefore) {
                    discount = 10;
                    amountAfterDiscount = bill.getTotalAmount() * DISCOUNT_BALANCE * DISCOUNT_BALANCE;
                } else {
                    discount = 5;
                    amountAfterDiscount = bill.getTotalAmount() * DISCOUNT_BALANCE;
                }


                PaymentTransaction transaction = new PaymentTransaction();
                transaction.setTransactionId("TXN_" + System.currentTimeMillis());
                transaction.setTransactionDateTime(LocalDateTime.now());
                transaction.setBillId(bill);
                transaction.setTransactionAmount(amountAfterDiscount);
                transaction.setDiscount(discount);
                transaction.setTransactionMethod(PaymentTransaction.TransactionMethod.Wallet);
                transaction.setTransactionStatus(PaymentTransaction.TransactionStatus.SUCCESS);
                bill.setStatus(ElectricityBill.StatusType.PAID);
                customer.setWallet(customer.getWallet() - amountAfterDiscount);
                transaction.setTransactionMessage("Wallet Payment successful");
                String subject = "Payment Confirmation for BBC Electricity Bill";
                String body = String.format("""
                                        Dear Customer,
                                        We are pleased to inform you that your payment for the electricity bill has been successfully processed.
                                        
                                        Payment Details:
                                        
                                        Bill Number: %s
                                        Payment Date: %s
                                        Amount Paid: ₹%.2f
                                        Payment Method: %s
                                        Thank you for your Bill payment. Your account is now up to date.
                                        
                                        If you have any questions or need further assistance, please feel free to contact our support team.
                                        
                                        Thank you & Regards.
                                        Bharat Bijili Corporation (BBC)
                                        Pune, Maharashtra""",bill.getBillId(),LocalDateTime.now(),amountAfterDiscount,PaymentTransaction.TransactionMethod.Wallet.toString());
                emailService.sendEmailByCustomerId(customerId, subject, body);
                transactionRepository.save(transaction);
                customerRepository.save(customer);
                electricityBillRepository.save(bill);

            }
        }
        return "Payment successful";
    }
}
