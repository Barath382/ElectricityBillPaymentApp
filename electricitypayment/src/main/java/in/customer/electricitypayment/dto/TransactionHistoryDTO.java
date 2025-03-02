package in.customer.electricitypayment.dto;

import in.customer.electricitypayment.model.PaymentTransaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistoryDTO {
    private String transactionId;
    private Double transactionAmount;
    private LocalDateTime transactionDateTime;
    private String transactionMethod;
    private String transactionMessage;
    private int discount;
    private String transactionStatus;
    private int units;
    private LocalDate billDate;
    private LocalDate dueDate;
    private String durationOfBill;
    private Double totalAmount;
    private String BillType;
    private Long billId;

}
