package in.customer.electricitypayment.service;

import in.customer.electricitypayment.dto.ElectricityBillDTO;
import in.customer.electricitypayment.model.Customer;
import in.customer.electricitypayment.model.ElectricityBill;
import in.customer.electricitypayment.repository.CustomerRepository;
import in.customer.electricitypayment.repository.ElectricityBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ElectricityBillService {

    @Autowired
    ElectricityBillRepository electricityBillRepository;

    @Autowired
    CustomerRepository customerRepository;

    public List<ElectricityBillDTO> getAllBillsByCustomerId(String customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findByCustomerId(customerId);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            return electricityBillRepository.findByCustomer(customer)
                    .stream()
                    .map(ElectricityBillDTO::new)
                    .sorted((bill1, bill2) -> bill1.getBillDate().compareTo(bill2.getBillDate()))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<ElectricityBillDTO> getDueBillsByCustomerId(String customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findByCustomerId(customerId);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            return electricityBillRepository.findByCustomerAndStatus(customer, ElectricityBill.StatusType.DUE)
                    .stream()
                    .map(ElectricityBillDTO::new)
                    .sorted((bill1, bill2) -> bill1.getBillDate().compareTo(bill2.getBillDate()))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<ElectricityBillDTO> getAllBillsForPayment(List<Long> billIds) {

        return electricityBillRepository.findByBillIdIn(billIds).stream()
                .map(ElectricityBillDTO::new)
                .sorted((bill1, bill2) -> bill1.getBillDate().compareTo(bill2.getBillDate()))
                .collect(Collectors.toList());
    }
}
