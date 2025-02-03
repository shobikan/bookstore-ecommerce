package org.bookstoreecommerce.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bookstoreecommerce.enums.BookCategory;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDTO {
    private String isbn;
    private String title;
    private String author;
    private double price;
    private int quantity;
    private BookCategory category;

}
