package org.bookstoreecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.bookstoreecommerce.service.ForgotPasswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/forgotPassword/{email}")
@RequiredArgsConstructor
public class ForgotPasswordController {
    private final ForgotPasswordService forgotPasswordService;

    @PostMapping
    public ResponseEntity<?> forgotPassword(@PathVariable("email") String email) {
        return ResponseEntity.ok(forgotPasswordService.forgotPassword(email));
    }

    @PutMapping("/verifyOtp")
    public ResponseEntity<?> verifyOtp(@PathVariable("email") String email, @RequestParam Integer otp) {
        return ResponseEntity.ok(forgotPasswordService.verifyOtp(otp, email));
    }

    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@PathVariable("email") String email, @RequestParam String password) {
        return ResponseEntity.ok(forgotPasswordService.changePassword(email, password));
    }
}
