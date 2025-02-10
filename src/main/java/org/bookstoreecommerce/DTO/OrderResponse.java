package org.bookstoreecommerce.DTO;

import lombok.Builder;
import lombok.Data;
import org.bookstoreecommerce.entity.OrderLine;
import org.bookstoreecommerce.entity.ShippingDetails;
import org.bookstoreecommerce.enums.OrderStatus;
import org.bookstoreecommerce.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private Long orderId;
    private BigDecimal totalAmount;
    private LocalDateTime createdDate;
    private List<OrderLine> orderLines;
    private Long userId;
    private String paymentId;
    private PaymentStatus paymentStatus;
    private ShippingDetails shippingDetails;
    private OrderStatus orderStatus;
}
