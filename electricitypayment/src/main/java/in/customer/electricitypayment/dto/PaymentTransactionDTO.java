package in.customer.electricitypayment.dto;

import in.customer.electricitypayment.model.ElectricityBill;
import in.customer.electricitypayment.model.PaymentTransaction;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentTransactionDTO {

    private Long pid;
    private String transactionId;
    private String transactionMethod;
    private Double transactionAmount;
    private LocalDateTime transactionDateTime;
    private String cardNumber;
    private String cardExpireDate;

    // Electricity Bill details
    private Long billId;
    private String billDuration;
    private Double billTotalAmount;
    private String billStatus;
    private String billType;

    // Customer details
    private String customerId;
    private String customerName;

    public PaymentTransactionDTO(PaymentTransaction transaction) {
        this.pid = transaction.getPid();
        this.transactionId = transaction.getTransactionId();
        this.transactionMethod = transaction.getTransactionMethod().name();
        this.transactionAmount = transaction.getTransactionAmount();
        this.transactionDateTime = transaction.getTransactionDateTime();
        this.cardNumber = transaction.getCardNumber();
        this.cardExpireDate = transaction.getCardExpireDate();

        ElectricityBill bill = transaction.getBillId();
        this.billId = bill.getBillId();
        this.billDuration = bill.getDurationOfBill();
        this.billTotalAmount = bill.getTotalAmount();
        this.billStatus = bill.getStatus().name();
        this.billType = bill.getBillType().name();

        if (bill.getCustomer() != null) {
            this.customerId = bill.getCustomer().getCustomerId();
            this.customerName = bill.getCustomer().getCustomerName();
        }
    }

    // Getters and Setters...
}
