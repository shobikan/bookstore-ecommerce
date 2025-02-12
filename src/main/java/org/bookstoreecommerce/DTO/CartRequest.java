package org.bookstoreecommerce.DTO;

import lombok.Data;

@Data
public class CartRequest {
    private Long bookId;
    private Long quantity;
}
