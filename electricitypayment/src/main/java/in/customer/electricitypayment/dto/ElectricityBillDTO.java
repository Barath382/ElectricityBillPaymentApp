package in.customer.electricitypayment.dto;


import in.customer.electricitypayment.model.ElectricityBill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElectricityBillDTO {

    private Long billId;
    private String customerId;
    private String customerName;
    private String email;
    private Double wallet;
    private int units;
    private LocalDate billDate;
    private LocalDate dueDate;
    private String durationOfBill;
    private Double totalAmount;
    private String BillType;
    private String status;

    public ElectricityBillDTO(ElectricityBill bill) {
        this.billId = bill.getBillId();
        this.customerId = bill.getCustomer().getCustomerId();
        this.customerName = bill.getCustomer().getCustomerName();
        this.email = bill.getCustomer().getEmail();
        this.wallet = bill.getCustomer().getWallet();
        this.units = bill.getUnits();
        this.billDate = bill.getBillDate();
        this.dueDate = bill.getDueDate();
        this.durationOfBill = bill.getDurationOfBill();
        this.totalAmount = bill.getTotalAmount();
        BillType = bill.getBillType().toString();
        this.status = bill.getStatus().toString();


    }

}