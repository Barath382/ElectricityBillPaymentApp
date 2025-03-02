package in.customer.electricitypayment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditDebitCard {
    @Id
    @Column(name = "card_number", length = 16, nullable = false, unique = true)
    private String cardNumber; // Card number as a string

    @Column(name = "expire_date", length = 7, nullable = false)
    private String expireDate; // Expiration date in MM/YYYY format

    @Column(name = "cvv", nullable = false)
    private int cvv; // 3-digit CVV number

    @Column(name = "customer_name", length = 100, nullable = false)
    private String customerName; // Customer's full name

    @Column(name = "email", length = 255, nullable = false)
    private String email; // Customer's email address

    @Column(name = "balance", nullable = false)
    private double balance; // Current balance in the card
}
