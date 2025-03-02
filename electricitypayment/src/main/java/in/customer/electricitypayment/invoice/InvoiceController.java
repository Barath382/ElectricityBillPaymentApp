package in.customer.electricitypayment.invoice;

import com.itextpdf.text.DocumentException;
import in.customer.electricitypayment.dto.PaymentTransactionDTO;
import in.customer.electricitypayment.model.PaymentTransaction;
import in.customer.electricitypayment.repository.PaymentTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    PaymentTransactionRepository paymentTransactionRepository;

    @GetMapping("/generate/{transactionId}")
    public ResponseEntity<byte[]> generateInvoiceByTransactionId (@PathVariable String transactionId) throws DocumentException {
        Optional<PaymentTransaction> optionalPaymentTransaction = paymentTransactionRepository.findByTransactionId(transactionId);
        if (optionalPaymentTransaction.isPresent()){
            PaymentTransaction paymentTransaction = optionalPaymentTransaction.get();
            return InvoiceService.generatePaymentInvoice(paymentTransaction);
        }
        return null;
    }
    @GetMapping("/generate-bill/{billId}")
    public ResponseEntity<byte[]> generateInvoiceByBillId (@PathVariable Long billId) throws DocumentException {
        PaymentTransaction paymentTransaction = paymentTransactionRepository.findByBillId_BillIdAndTransactionStatus(billId, PaymentTransaction.TransactionStatus.SUCCESS);
        return InvoiceService.generatePaymentInvoice(paymentTransaction);
    }


}
