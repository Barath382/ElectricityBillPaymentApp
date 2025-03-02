package in.customer.electricitypayment.service;

import in.customer.electricitypayment.model.Customer;
import in.customer.electricitypayment.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpAuthService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private OTPService otpService;

    public String generateOtp(String customerId) {
        Optional<Customer> customerOptional = customerRepository.findByCustomerId(customerId);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            String otp = otpService.generateOTP(customerId);
            String email = customer.getEmail();
            String subject = "OTP Verification for BBC Login";
            String body = String.format("""
                                        Dear Customer,
                                        Please use the OTP below to login to our portal:
                                        OTP: %s
                                        
                                        Thank you & Regards.
                                        Bharat Bijili Corporation (BBC)
                                        Pune, Maharashtra
                                        """, otp);
            emailService.sendEmail(email, subject, body);
            return otp;
        }
        throw new RuntimeException("Customer not found");
    }


    public boolean validateOtp(String customerId, String otp) {
        boolean isValid = otpService.validateOTP(customerId,otp);
        if (isValid){
            return true;
        } else {
            throw new RuntimeException("Invalid OTP or OTP has expired");
        }
    }

    public String resendOtp(String customerId) {
        return generateOtp(customerId);
    }

    public Customer getCustomerInfo(String customerId){
        Optional<Customer> customerOptional = customerRepository.findByCustomerId(customerId);
        if (customerOptional.isPresent()){
            return customerOptional.get();
        }
        throw  new RuntimeException("Customer Not Found");
    }

    public Customer updateWallet(String customerId, Double amountToAdd) {
        Optional<Customer> existingCustomer = customerRepository.findByCustomerId(customerId);
        if (existingCustomer.isPresent()) {
            Customer customer = existingCustomer.get();
            customer.setWallet(customer.getWallet() + amountToAdd);
            return customerRepository.save(customer);
        }
        throw  new RuntimeException("Customer Not Found");
    }
}
