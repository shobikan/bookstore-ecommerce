package org.bookstoreecommerce.service.impl;

import org.bookstoreecommerce.DTO.BookDTO;
import org.bookstoreecommerce.entity.Book;
import org.bookstoreecommerce.enums.BookCategory;
import org.bookstoreecommerce.repository.BookRepository;
import org.bookstoreecommerce.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public BookDTO getBookByIsbn(String isbn) {
        return bookRepository.findById(isbn)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        if (bookRepository.existsById(bookDTO.getIsbn())) {
            throw new RuntimeException("Book already exists with ISBN " + bookDTO.getIsbn());
        }
        Book book = Book.builder()
                .isbn(bookDTO.getIsbn())
                .title(bookDTO.getTitle())
                .author(bookDTO.getAuthor())
                .price(bookDTO.getPrice())
                .quantity(bookDTO.getQuantity())
                .category(bookDTO.getCategory())
                .build();
        bookRepository.save(book);
        return bookDTO;
    }

    @Override
    public BookDTO updateBook(String isbn, BookDTO bookDTO) {
        Book book = bookRepository.findById(isbn).orElseThrow(() -> new RuntimeException("Book not found"));
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPrice(bookDTO.getPrice());
        book.setQuantity(bookDTO.getQuantity());
        book.setCategory(bookDTO.getCategory());
        bookRepository.save(book);
        return bookDTO;
    }

    @Override
    public String deleteBook(String isbn) {
        if (!bookRepository.existsById(isbn)) {
            return "The book with ISBN " + isbn + " does not exist.";
        }
        bookRepository.deleteById(isbn);
        return isbn;
    }

    @Override
    public List<BookDTO> getBooksByCategory(String category) {
        BookCategory categoryEnum = BookCategory.valueOf(category);
        return bookRepository.findByCategory(categoryEnum).stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<BookDTO> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author).stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<BookDTO> getBooksByTitle(String title) {
        return bookRepository.findByTitle(title).stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public Integer getBookQuantity(String isbn) {
        return bookRepository.findQuantityByIsbn(isbn);
    }

    private BookDTO convertToDTO(Book book) {
        return BookDTO.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice())
                .quantity(book.getQuantity())
                .category(book.getCategory())
                .build();
    }
}
