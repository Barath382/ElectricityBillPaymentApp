package in.customer.electricitypayment.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String customerId;
    private String customerName;
    private String phoneNumber;
    private String email;
    private String address;
    private LocalDateTime customerGeneratedDate;
    private double Wallet = 0.0;


}
