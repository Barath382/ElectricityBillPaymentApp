package in.customer.electricitypayment.repository;

import in.customer.electricitypayment.model.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
    List<PaymentTransaction> findByBillId_BillIdIn(List<Long> billIds);
    PaymentTransaction findByBillId_BillIdAndTransactionStatus(Long billIds,PaymentTransaction.TransactionStatus status);
    Optional<PaymentTransaction> findByTransactionId(String transactionId);
    List<PaymentTransaction> findByTransactionIdIn(List<String> transactionId);
    List<PaymentTransaction> findByBillIdCustomerCustomerIdAndTransactionStatus(String customerId, PaymentTransaction.TransactionStatus status);
}
