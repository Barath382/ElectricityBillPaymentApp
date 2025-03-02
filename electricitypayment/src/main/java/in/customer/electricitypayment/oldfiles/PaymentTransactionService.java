package in.customer.electricitypayment.oldfiles;

import in.customer.electricitypayment.dto.PaymentRequestDTO;
import in.customer.electricitypayment.dto.PaymentTransactionDTO;
import in.customer.electricitypayment.model.PaymentTransaction;
import in.customer.electricitypayment.repository.CustomerRepository;
import in.customer.electricitypayment.repository.ElectricityBillRepository;
import in.customer.electricitypayment.repository.PaymentTransactionRepository;
import in.customer.electricitypayment.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaymentTransactionService {

    public static final Double DISCOUNT_BALANCE = 0.95;

    @Autowired
    PaymentTransactionRepository paymentTransactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    ElectricityBillRepository electricityBillRepository;

    @Autowired
    private EmailService emailService;

    public List<PaymentTransaction> getAllPaymentTransaction(){
        return paymentTransactionRepository.findAll();
    }

    Map<String,String> billOtp = new HashMap<>();

//    @Autowired
//    private CreditDebitCardRepository cardRepository;
//
//    public boolean validateCardPayment(String cardNumber, String expireDate, int cvv, double paymentAmount) {
//        // Fetch card details
//        return cardRepository.findByCardNumberAndExpireDateAndCvv(cardNumber, expireDate, cvv)
//                .map(card -> {
//                    if (card.getBalance() >= paymentAmount) {
//                        // Deduct balance
//                        card.setBalance(card.getBalance() - paymentAmount);
//                        cardRepository.save(card); // Save the updated balance
//                        return true;
//                    }
//                    return false; // Insufficient balance
//                })
//                .orElse(false); // Invalid card details
//    }

    public String generateOtp(String billId) {
            String otp = String.format("%06d", new Random().nextInt(999999));
            String subject = "OTP Verification for BBC Login";
            String body = String.format("Dear Customer,\n\nPlease use the OTP below to confirm payment:\nOTP: %s", otp);
            emailService.sendEmail("barath20td0808@svcet.ac.in", subject, body);
            billOtp.put(billId,otp);
            return otp;
    }



    public void validatePayment(PaymentRequestDTO paymentRequest){
        String billId = String.valueOf(paymentRequest.getBillIds().getFirst());

        String otp =  generateOtp(billId);

    }

    public void proceedPayment(PaymentRequestDTO paymentRequest) {

//        for (Long billId : paymentRequest.getBillIds()) {
//            ElectricityBill bill = electricityBillRepository.findById(billId)
//                    .orElseThrow(() -> new RuntimeException("Bill not found with id " + billId));
//
//            double amountAfterDiscount = 0;
//            int discount = 0;
//            LocalDate today = LocalDate.now();
//            LocalDate dueDate = bill.getDueDate();
//            boolean isBefore = dueDate.isBefore(today);
//            if (!isBefore){
//                discount = 10;
//                amountAfterDiscount = bill.getTotalAmount() * DISCOUNT_BALANCE * DISCOUNT_BALANCE;
//            } else{
//                amountAfterDiscount = bill.getTotalAmount() * DISCOUNT_BALANCE;
//            }
//
//            String customerId = bill.getCustomer().getCustomerId();
//            // Create a new payment transaction for each bill
//            PaymentTransaction paymentTransaction = new PaymentTransaction();
//            paymentTransaction.setTransactionId("TXN_" + System.currentTimeMillis()); // Generate unique transaction ID
//            paymentTransaction.setTransactionMethod(paymentRequest.getTransactionMethod());
//            paymentTransaction.setTransactionAmount(amountAfterDiscount);
//            paymentTransaction.setTransactionDateTime(LocalDateTime.now());
//            paymentTransaction.setDiscount(discount);
//            paymentTransaction.setBillId(bill);
//            paymentTransaction.setCardNumber(paymentRequest.getCardNumber());
//            paymentTransaction.setCardExpireDate(paymentRequest.getCardExpireDate());
//            paymentTransaction.setCardCvv(paymentRequest.getCardCvv());
//            paymentTransaction.setTransactionStatus(PaymentTransaction.TransactionStatus.SUCCESS);
//            // Save the payment transaction
//            paymentTransactionRepository.save(paymentTransaction);
//            // Mark the bill as PAID
//            bill.setStatus(ElectricityBill.StatusType.PAID);
//            electricityBillRepository.save(bill);
//        }

    }

    public List<PaymentTransactionDTO> getPaymentTransactionsByBillIds(List<Long> billIds) {

        List<PaymentTransaction> transactions = paymentTransactionRepository.findByBillId_BillIdIn(billIds);

        return transactions.stream()
                .map(PaymentTransactionDTO::new)
                .collect(Collectors.toList());
    }
}
