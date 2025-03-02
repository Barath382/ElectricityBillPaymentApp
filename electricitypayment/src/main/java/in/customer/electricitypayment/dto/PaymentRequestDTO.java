package in.customer.electricitypayment.dto;

import in.customer.electricitypayment.model.PaymentTransaction;
import lombok.Data;

import java.util.List;

@Data
public class PaymentRequestDTO {
    private List<Long> billIds;
    private String cardNumber;
    private String cardExpireDate;
    private int cardCvv;
    private double payAmount;
    private PaymentTransaction.TransactionMethod transactionMethod;
}
