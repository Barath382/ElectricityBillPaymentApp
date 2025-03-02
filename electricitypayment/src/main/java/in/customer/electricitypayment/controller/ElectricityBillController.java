package in.customer.electricitypayment.controller;

import in.customer.electricitypayment.dto.ElectricityBillDTO;
import in.customer.electricitypayment.service.ElectricityBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/bills")
public class ElectricityBillController {

    @Autowired
    ElectricityBillService electricityBillService;


    @GetMapping("/customer")
    public ResponseEntity<List<ElectricityBillDTO>> getAllBillsByCustomerId(@RequestParam String customerId) {
        List<ElectricityBillDTO> bills = electricityBillService.getAllBillsByCustomerId(customerId);
        if (bills == null || bills.isEmpty()) {return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }


    @GetMapping("/due")
    public ResponseEntity<List<ElectricityBillDTO>> getDueBillsByCustomerId(@RequestParam String customerId) {
        List<ElectricityBillDTO> dueBills = electricityBillService.getDueBillsByCustomerId(customerId);
        if (dueBills == null || dueBills.isEmpty()) { return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        return new ResponseEntity<>(dueBills, HttpStatus.OK);
    }

    @GetMapping("/bill")
    public ResponseEntity<List<ElectricityBillDTO>> getAllBillsForPayment(@RequestParam List<Long> billIds){
        List<ElectricityBillDTO> bills = electricityBillService.getAllBillsForPayment(billIds);
        if (bills == null || bills.isEmpty()) {return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }
}
