package org.bookstoreecommerce.service;

import org.bookstoreecommerce.DTO.BookDTO;

import java.util.List;

public interface BookService {
    List<BookDTO> getAllBooks();
    BookDTO getBookByIsbn(String isbn);
    BookDTO addBook(BookDTO bookDTO);
    BookDTO updateBook(String isbn, BookDTO bookDTO);
    String deleteBook(String isbn);
    List<BookDTO> getBooksByCategory(String category);
    List<BookDTO> getBooksByAuthor(String author);
    List<BookDTO> getBooksByTitle(String title);
    Integer getBookQuantity(String isbn);

}
