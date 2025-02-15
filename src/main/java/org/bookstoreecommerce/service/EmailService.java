package org.bookstoreecommerce.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.bookstoreecommerce.DTO.EmailRequest;
import org.bookstoreecommerce.entity.EmailLog;
import org.bookstoreecommerce.repository.EmailLogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final EmailLogRepository emailLogRepository;

    @Value("${spring.mail.username}")
    private String from;

    public Boolean sendEmailWithLog(EmailRequest request) {
        try {
            sendMail(request);
            saveEmailLog(request, "SENT");
            return true;
        } catch (MessagingException e) {
            saveEmailLog(request, "FAILED");
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public Boolean sendEmailSimple(EmailRequest request) {
        try {
            sendMail(request);
            return true;
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public List<EmailLog> getEmailLogs() {
        return emailLogRepository.findAll();
    }

    public List<EmailLog> getEmailLogsByRecipient(String recipient) {
        return emailLogRepository.findByRecipientOrderByMailLogIdDesc(recipient);
    }

    public List<EmailLog> getEmailLogsByStatus(String status) {
        return emailLogRepository.findByStatusOrderByMailLogIdDesc(status);
    }

    private void sendMail (EmailRequest request) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(from);
        helper.setTo(request.getTo());
        helper.setSubject(request.getSubject());
        helper.setText(request.getBody(), true);

        mailSender.send(message);
    }

    private void saveEmailLog(EmailRequest request, String status) {
        EmailLog emailLog = new EmailLog();
        emailLog.setRecipient(request.getTo());
        emailLog.setSubject(request.getSubject());
        emailLog.setBody(request.getBody());
        emailLog.setStatus(status);
        emailLogRepository.save(emailLog);
    }
}
