package org.bookstoreecommerce.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {
    private String sessionId;
    private String checkoutUrl;
}
