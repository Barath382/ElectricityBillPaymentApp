package in.customer.electricitypayment.controller;

import in.customer.electricitypayment.dto.TransactionHistoryDTO;
import in.customer.electricitypayment.service.TransactionHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/transaction")
public class TransactionHistoryController {


    private TransactionHistoryService transactionHistoryService;

    public TransactionHistoryController(TransactionHistoryService transactionHistoryService) {
        this.transactionHistoryService = transactionHistoryService;
    }

    @GetMapping("/successful-transactions/{customerId}")
    public List<TransactionHistoryDTO> getSuccessfulTransactionsByCustomerId(@PathVariable String customerId) {
        return transactionHistoryService.getAllTransactionsByCustomerId(customerId);
    }

}
