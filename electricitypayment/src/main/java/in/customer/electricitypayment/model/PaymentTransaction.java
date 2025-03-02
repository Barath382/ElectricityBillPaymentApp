package in.customer.electricitypayment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTransaction {

    public enum TransactionMethod {
        CARD, CASH, Wallet
    }

    public enum TransactionStatus {
        SUCCESS, FAILED, PROCESS
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    @Column(unique = true)
    private String transactionId;

    @Enumerated(EnumType.STRING)
    private TransactionMethod transactionMethod;

    private double transactionAmount = 0.0;

    private int discount;

    private LocalDateTime transactionDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id", referencedColumnName = "bill_id",nullable = false)
    @JsonIgnore
    private ElectricityBill billId;

    private String cardNumber;

    private String cardExpireDate;

    private int cardCvv;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus = TransactionStatus.PROCESS;

    private String transactionMessage;

}
