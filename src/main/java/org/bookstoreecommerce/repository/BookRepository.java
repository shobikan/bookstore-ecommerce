package org.bookstoreecommerce.repository;

import org.bookstoreecommerce.entity.Book;
import org.bookstoreecommerce.enums.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findByCategory(BookCategory category);
    List<Book> findByAuthor(String author);
    List<Book> findByTitle(String title);
    @Query("SELECT b.quantity FROM Book b WHERE b.isbn = :isbn")
    Integer findQuantityByIsbn(@Param("isbn") String isbn);
}
