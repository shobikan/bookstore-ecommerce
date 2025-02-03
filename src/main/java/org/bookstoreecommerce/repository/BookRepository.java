package org.bookstoreecommerce.repository;

import org.bookstoreecommerce.entity.Book;
import org.bookstoreecommerce.enums.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findByCategory(BookCategory category);
    List<Book> findByAuthor(String author);
    List<Book> findByTitle(String title);
    Integer findQuantityByIsbn(String isbn);
}
