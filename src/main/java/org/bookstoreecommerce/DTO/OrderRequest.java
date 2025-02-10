package org.bookstoreecommerce.DTO;

import lombok.Data;
import org.bookstoreecommerce.entity.OrderLine;
import org.bookstoreecommerce.entity.ShippingDetails;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderRequest {
    private BigDecimal totalAmount;
    private List<OrderLine> orderLines;
    private Long userId;
    private ShippingDetails shippingDetails;
}
