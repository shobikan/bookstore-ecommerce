package org.bookstoreecommerce.DTO;

import lombok.Builder;
import lombok.Data;
import org.bookstoreecommerce.enums.OrderStatus;
import org.bookstoreecommerce.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class OrderResponse {
    private Long orderId;
    private BigDecimal totalAmount;
    private LocalDateTime createdDate;
    private Long userId;
    private String paymentId;
    private PaymentStatus paymentStatus;
    private OrderStatus orderStatus;
}
