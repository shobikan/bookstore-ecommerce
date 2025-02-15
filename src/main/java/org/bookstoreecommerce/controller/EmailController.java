package org.bookstoreecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.bookstoreecommerce.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllEmailLogs() {
        return ResponseEntity.ok(emailService.getEmailLogs());
    }

    @GetMapping("/{recipient}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getEmailLogsByRecipient(@PathVariable("recipient") String recipient) {
        return ResponseEntity.ok(emailService.getEmailLogsByRecipient(recipient));
    }

    @GetMapping("/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getEmailLogsByStatus(@PathVariable("status") String status) {
        return ResponseEntity.ok(emailService.getEmailLogsByStatus(status));
    }
}
