package org.bookstoreecommerce.DTO;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long amount;  // Price in cents (e.g., $10.00 = 1000)
    private String currency;
    private Long quantity;
    private String orderId;
}
