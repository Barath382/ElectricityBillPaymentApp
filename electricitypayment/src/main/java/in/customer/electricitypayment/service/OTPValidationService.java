package in.customer.electricitypayment.service;

import in.customer.electricitypayment.model.CreditDebitCard;
import in.customer.electricitypayment.model.ElectricityBill;
import in.customer.electricitypayment.model.PaymentTransaction;
import in.customer.electricitypayment.repository.CreditDebitCardRepository;
import in.customer.electricitypayment.repository.ElectricityBillRepository;
import in.customer.electricitypayment.repository.PaymentTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OTPValidationService {

    @Autowired
    private OTPService otpService; // Service to validate OTP

    @Autowired
    private ElectricityBillRepository electricityBillRepository;

    @Autowired
    private PaymentTransactionRepository transactionRepository;

    @Autowired
    private CreditDebitCardRepository cardRepository;

    @Autowired
    EmailService emailService;

    public String validateOTP(String otp, String cardNumber, List<String> transactionId) {

        if (!otpService.validateOTP(cardNumber, otp)) {
        for(String id : transactionId){
            Optional<PaymentTransaction> optionalTransaction = transactionRepository.findByTransactionId(id);
            if (optionalTransaction.isEmpty()) {
                return "Transaction not found";
            }
            PaymentTransaction transaction = optionalTransaction.get();
                transaction.setTransactionStatus(PaymentTransaction.TransactionStatus.FAILED);
                transaction.setTransactionMessage("Invalid OTP");
                transactionRepository.save(transaction);
            }
                return "Invalid OTP";
        }

        Optional<CreditDebitCard> optionalCard = cardRepository.findByCardNumber(cardNumber);
        if (optionalCard.isEmpty()) {

            for(String id : transactionId){
                Optional<PaymentTransaction> optionalTransaction = transactionRepository.findByTransactionId(id);
                if (optionalTransaction.isEmpty()) {
                    return "Transaction not found";
                }
                PaymentTransaction transaction = optionalTransaction.get();

                transaction.setTransactionStatus(PaymentTransaction.TransactionStatus.FAILED);
                transaction.setTransactionMessage("Card not found");
                transactionRepository.save(transaction);
            }
            return "Card not found";
        }


        for(String id : transactionId) {
            Optional<PaymentTransaction> optionalTransaction = transactionRepository.findByTransactionId(id);
            if (optionalTransaction.isEmpty()) {
                return "Transaction not found";
            }
            PaymentTransaction transaction = optionalTransaction.get();
            CreditDebitCard card = optionalCard.get();
            card.setBalance(card.getBalance() - transaction.getTransactionAmount());
            Optional<ElectricityBill> billOptional = electricityBillRepository.findById(transaction.getBillId().getBillId());
            ElectricityBill bill = billOptional.get();
            cardRepository.save(card);
            bill.setStatus(ElectricityBill.StatusType.PAID);
            electricityBillRepository.save(bill);
            transaction.setTransactionStatus(PaymentTransaction.TransactionStatus.SUCCESS);
            transaction.setTransactionMessage("Card Payment successful");
            String customerId = bill.getCustomer().getCustomerId();
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
                                        Pune, Maharashtra""",bill.getBillId(), LocalDateTime.now(),transaction.getTransactionAmount(),PaymentTransaction.TransactionMethod.CARD.toString());
            emailService.sendEmailByCustomerId(customerId, subject, body);
            transactionRepository.save(transaction);
        }
        return "Payment successful";
    }

}
