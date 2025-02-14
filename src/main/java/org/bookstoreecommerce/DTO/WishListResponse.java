package org.bookstoreecommerce.DTO;

import lombok.Builder;
import lombok.Data;
import org.bookstoreecommerce.enums.BookCategory;

@Data
@Builder
public class WishListResponse {
    private String isbn;
    private String title;
    private String author;
    private double price;
    private BookCategory category;
}
