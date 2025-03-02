package in.customer.electricitypayment.repository;

import in.customer.electricitypayment.model.Customer;
import in.customer.electricitypayment.model.ElectricityBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ElectricityBillRepository extends JpaRepository<ElectricityBill, Long> {

    List<ElectricityBill> findByCustomer(Customer customer);
    List<ElectricityBill> findByCustomerAndStatus(Customer customer, ElectricityBill.StatusType status);
    List<ElectricityBill> findByBillIdIn(List<Long> billIds);
}
