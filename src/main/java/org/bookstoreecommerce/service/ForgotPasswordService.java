package org.bookstoreecommerce.service;

import lombok.RequiredArgsConstructor;
import org.bookstoreecommerce.DTO.EmailRequest;
import org.bookstoreecommerce.entity.ForgotPassword;
import org.bookstoreecommerce.entity.User;
import org.bookstoreecommerce.repository.ForgotPasswordRepository;
import org.bookstoreecommerce.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public String forgotPassword(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Integer otp = otpGenerator();
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(email);
        emailRequest.setSubject("Password Reset OTP");
        emailRequest.setBody("Your OTP is: " + otp + ". This OTP will expire in 10 minutes.");
        emailService.sendEmailSimple(emailRequest);

        ForgotPassword forgotPassword = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 60 * 10 * 1000)) // 10 minutes
                .user(user)
                .build();
        forgotPasswordRepository.save(forgotPassword);

        return "OTP sent to your email";
    }

    public String verifyOtp(Integer otp, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        ForgotPassword forgotPassword = forgotPasswordRepository.findByOtpAndUser(otp, user)
                .orElseThrow(() -> new IllegalArgumentException("Invalid OTP for " + email));

        if(forgotPassword.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordRepository.delete(forgotPassword);
            return "OTP expired";
        }

        forgotPassword.setIsVerified(true);
        forgotPasswordRepository.save(forgotPassword);

        return "OTP verified";
    }

    public String changePassword(String email, String password){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        ForgotPassword forgotPassword = forgotPasswordRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("OTP verification required before changing password"));

        if(!forgotPassword.getIsVerified()){
            return "OTP verification required before changing password";
        }

        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        forgotPasswordRepository.deleteById(forgotPassword.getForgotPasswordId());

        return "Password reset successfully";
    }

    private Integer otpGenerator(){
        Random random = new Random();
        return random.nextInt(100000,999999);
    }
}
