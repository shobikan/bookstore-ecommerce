package org.bookstoreecommerce.DTO;

import lombok.Data;

@Data
public class CartRequest {
    private String isbn;
    private int quantity;
}
