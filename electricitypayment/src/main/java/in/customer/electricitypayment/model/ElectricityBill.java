package in.customer.electricitypayment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "electricity_bill")
public class ElectricityBill {

    public  enum StatusType{
        PAID,DUE
    }
    public enum BillType {
        CORPORATE, RESIDENTIAL
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private Long billId;

    // Many-to-one relationship with Customer, referencing the 'customerId' field in Customer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customerId", nullable = false)  // References 'customerId' in Customer
    private Customer customer;

    @Column(name = "units")
    private int units;

    @Enumerated(EnumType.STRING)
    private BillType billType = BillType.RESIDENTIAL;

    @Column(name = "bill_date")
    private LocalDate billDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "bill_duration")
    private String durationOfBill;

    @Column(name = "total_amount")
    private double totalAmount = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(name = "bill_status")
    private StatusType status = StatusType.DUE;

}
