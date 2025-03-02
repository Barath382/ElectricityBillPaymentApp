package in.customer.electricitypayment.controller;

import in.customer.electricitypayment.dto.PaymentRequestDTO;
import in.customer.electricitypayment.model.ElectricityBill;
import in.customer.electricitypayment.model.PaymentTransaction;
import in.customer.electricitypayment.service.OTPValidationService;
import in.customer.electricitypayment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/payment-request")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;


    @Autowired
    private OTPValidationService otpValidationService;

    @PostMapping("/validate-card")
    public ResponseEntity<?> validateCard( @RequestBody PaymentRequestDTO paymentRequestDTO ) {

        Map<String, List<String>> result = paymentService.processPayment(paymentRequestDTO);
        List<String> errorMessages = result.get("message");

        if (errorMessages != null && (errorMessages.contains("Invalid card details") || errorMessages.contains("Insufficient balance"))) {
            return ResponseEntity.badRequest().body(result);
        }
        String resultParts = "OTP has been sent to your email.";
        Map<String, List<String>> response = new HashMap<>();
        response.put("message", List.of(resultParts));
        response.put("cardNumber",List.of(paymentRequestDTO.getCardNumber()));
        response.put("transactionId", result.get("transaction"));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<?> validateOTP(
            @RequestParam("otp") String otp,
            @RequestParam("cardNumber") String cardNumber,
            @RequestParam("transactionId") List<String> transactionId
    ) {
            String result = otpValidationService.validateOTP(otp, cardNumber, transactionId);

        if (result.equals("Invalid OTP") || result.equals("Transaction not found") || result.equals("Card not found")) {
            return ResponseEntity.badRequest().body(result);
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", result);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate-wallet")
    public  ResponseEntity<?> validateWallet(@RequestBody PaymentRequestDTO paymentRequestDTO ,@RequestParam String customerId){
        String result = paymentService.processWalletPayment(paymentRequestDTO,customerId);

        if (result != null && (result.contains("Insufficient Balance in Wallet") || result.contains("Customer not found"))) {
            return ResponseEntity.badRequest().body(result);
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", result);
        return ResponseEntity.ok(response);
    }



}
