package in.customer.electricitypayment.oldfiles;

import in.customer.electricitypayment.dto.PaymentRequestDTO;
import in.customer.electricitypayment.dto.PaymentTransactionDTO;
import in.customer.electricitypayment.model.PaymentTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/payment")
public class PaymentTransactionController {

    @Autowired
    PaymentTransactionService paymentTransactionService;

    @GetMapping("/get")
    public List<PaymentTransaction> getPaymentTransaction(){
        return paymentTransactionService.getAllPaymentTransaction();
    }

   @PostMapping("/request")
    public String proceedPayment(@RequestBody PaymentRequestDTO paymentRequestDTO)
   {
//        paymentTransactionService.proceedPayment(paymentRequestDTO);
       paymentTransactionService.validatePayment(paymentRequestDTO);
        return "Successfull";
   }

    @GetMapping("/transactions")
    public List<PaymentTransactionDTO> getPaymentTransactionsByBillIds(@RequestParam List<Long> billIds) {
        return paymentTransactionService.getPaymentTransactionsByBillIds(billIds);
    }

}
