package in.customer.electricitypayment.service;

import in.customer.electricitypayment.dto.TransactionHistoryDTO;
import in.customer.electricitypayment.model.PaymentTransaction;
import in.customer.electricitypayment.repository.PaymentTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionHistoryService {

    @Autowired
    private PaymentTransactionRepository paymentTransactionRepository;

    public List<TransactionHistoryDTO> getAllTransactionsByCustomerId(String customerId) {
        List<PaymentTransaction> transactions = paymentTransactionRepository
                .findByBillIdCustomerCustomerIdAndTransactionStatus(customerId, PaymentTransaction.TransactionStatus.SUCCESS);

        List<PaymentTransaction> transactions2 = paymentTransactionRepository
                .findByBillIdCustomerCustomerIdAndTransactionStatus(customerId, PaymentTransaction.TransactionStatus.FAILED);

        transactions.addAll(transactions2);
        return transactions
                .stream()
                .sorted(Comparator.comparing(PaymentTransaction::getTransactionDateTime).reversed())
                .map(transaction -> new TransactionHistoryDTO(
                transaction.getTransactionId(),
                transaction.getTransactionAmount(),
                transaction.getTransactionDateTime(),
                transaction.getTransactionMethod().toString(),
                transaction.getTransactionMessage(),
                transaction.getDiscount(),
                transaction.getTransactionStatus().toString(),
                transaction.getBillId().getUnits(),
                transaction.getBillId().getBillDate(),
                transaction.getBillId().getDueDate(),
                transaction.getBillId().getDurationOfBill(),
                transaction.getBillId().getTotalAmount(),
                transaction.getBillId().getBillType().toString(),
                transaction.getBillId().getBillId()
        ))
                .collect(Collectors.toList());
    }
    
}
