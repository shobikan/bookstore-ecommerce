package org.bookstoreecommerce.controller;

import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.bookstoreecommerce.DTO.PaymentRequest;
import org.bookstoreecommerce.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;


    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequest paymentRequest) throws StripeException {
        return ResponseEntity.ok(paymentService.createPaymentSession(paymentRequest));
    }

    // for testing purposes
    @PostMapping("/confirm")
    public ResponseEntity<?> confirmPayment(@RequestBody String sessionId) throws StripeException {
        return ResponseEntity.ok(paymentService.processPaymentAndUpdateOrder(sessionId));
    }

}
