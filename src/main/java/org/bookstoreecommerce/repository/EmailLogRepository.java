package org.bookstoreecommerce.repository;

import org.bookstoreecommerce.entity.EmailLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailLogRepository extends JpaRepository<EmailLog, Long> {
    List<EmailLog> findByRecipientOrderByMailLogIdDesc(String email);
    List<EmailLog> findByStatusOrderByMailLogIdDesc(String status);
}
