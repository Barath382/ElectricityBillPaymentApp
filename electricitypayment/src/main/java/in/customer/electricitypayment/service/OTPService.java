package in.customer.electricitypayment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OTPService {

    @Autowired
    private EmailService emailService;

    private Map<String, OTPData> otpData = new HashMap<>();


    private static class OTPData {
        String otp;
        LocalDateTime expiryTime;

        OTPData(String otp, LocalDateTime expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
        }
    }
    public String generateOTP(String customerId){
        Random random = new Random();
        String otp = String.format("%06d", random.nextInt(1000000));
        LocalDateTime expiryTime = LocalDateTime.now().plus(3, ChronoUnit.MINUTES);

        otpData.put(customerId, new OTPData(otp, expiryTime));
        return otp;
    }
    public String generateOTPForPayment(String cardNumber, String email) {
        String otp = generateOTP(cardNumber);
        String subject = "OTP Verification for BBC Bill Payment";
        String body = String.format("""
                                        Dear Customer,
                                        Please use the OTP below to confirm the payment:
                                        OTP: %s
                                        
                                        Thank you & Regards.
                                        Bharat Bijili Corporation (BBC)
                                        Pune, Maharashtra""", otp);
        emailService.sendEmail(email, subject, body);
        System.out.println("OTP for " + cardNumber + " mail id: " + otp);
        return otp;
    }

    public boolean validateOTP(String cardNumber, String otp) {
        OTPData otpDataEntry = otpData.get(cardNumber);

        if (otpDataEntry == null) {
            return false;
        }
        boolean isValid = otpDataEntry.otp.equals(otp) && LocalDateTime.now().isBefore(otpDataEntry.expiryTime);
        if (isValid) {
            otpData.remove(cardNumber);
        }
        return isValid;
    }



}
