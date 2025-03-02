package in.customer.electricitypayment.repository;

import in.customer.electricitypayment.model.CreditDebitCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditDebitCardRepository extends JpaRepository<CreditDebitCard,String> {
    Optional<CreditDebitCard> findByCardNumberAndExpireDateAndCvv(String cardNumber, String expireDate, int cvv);

    Optional<CreditDebitCard> findByCardNumber(String cardNumber);
}
