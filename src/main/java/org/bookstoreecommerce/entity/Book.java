package org.bookstoreecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bookstoreecommerce.enums.BookCategory;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    @Id
    private String isbn;  // ISBN as the primary key
    private String title;
    private String author;
    private double price;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private BookCategory category;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLine> orderLines;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems;

    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    private List<WishList> wishLists;

}
