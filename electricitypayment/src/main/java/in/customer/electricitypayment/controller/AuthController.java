package in.customer.electricitypayment.controller;

import in.customer.electricitypayment.model.Customer;
import in.customer.electricitypayment.service.OtpAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    OtpAuthService otpAuthService;

    public AuthController(OtpAuthService otpAuthService) {
        this.otpAuthService = otpAuthService;
    }

    @PostMapping("/send-otp")
    public ResponseEntity<Map<String, String>> sendOTP(@RequestParam("customerId") String customerId) {
        String otp = otpAuthService.generateOtp(customerId);
        Map<String, String> response = new HashMap<>();

        if (otp.length() <= 6) {
            response.put("message", "OTP was sent successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Error sending OTP");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<Map<String, String>> validateOtp(@RequestParam String customerId, @RequestParam String otp) {
        Map<String, String> response = new HashMap<>();
        if (otpAuthService.validateOtp(customerId, otp)) {
            Customer customer = otpAuthService.getCustomerInfo(customerId);
            response.put("message", "Login successful");
            response.put("customerId",customer.getCustomerId());
            response.put("customerName",customer.getCustomerName());
            return ResponseEntity.ok(response);
        }
        response.put("message", "Invalid or expired OTP");

        return ResponseEntity.status(401).body(response);
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<Map<String, String>> resendOtp(@RequestParam String customerId) {
        String otp = otpAuthService.resendOtp(customerId);
        Map<String, String> response = new HashMap<>();
        if (otp.length() <= 6) {
            response.put("message", "OTP was sent successfully");
            response.put("otp", otp); // In production, you may want to remove this line
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Error sending OTP");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-customer/{customerId}")
    public Customer getCustomerDetails(@PathVariable String customerId){
        return otpAuthService.getCustomerInfo(customerId);
    }

    @PutMapping("/wallet/{customerId}")
    public ResponseEntity<Customer> updateWallet(@PathVariable String customerId, @RequestParam Double amount) {
        Customer updatedCustomer = otpAuthService.updateWallet(customerId, amount);
        if (updatedCustomer != null) {
            return ResponseEntity.ok(updatedCustomer);
        }
        return ResponseEntity.notFound().build(); // Return 404 if not found
    }

}
