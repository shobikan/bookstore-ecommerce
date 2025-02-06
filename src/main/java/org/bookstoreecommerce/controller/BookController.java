package org.bookstoreecommerce.controller;

import org.bookstoreecommerce.DTO.BookDTO;
import org.bookstoreecommerce.service.BookService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{isbn}")
    @PreAuthorize("isAuthenticated()")
    public BookDTO getBookByIsbn(@PathVariable("isbn") String isbn) {
        return bookService.getBookByIsbn(isbn);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public BookDTO addBook(@RequestBody BookDTO bookDTO) {
        return bookService.addBook(bookDTO);
    }

    @PutMapping("/update/{isbn}")
    @PreAuthorize("hasRole('ADMIN')")
    public BookDTO updateBook(@PathVariable("isbn") String isbn, @RequestBody BookDTO bookDTO) {
        return bookService.updateBook(isbn, bookDTO);
    }

    @DeleteMapping("/delete/{isbn}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteBook(@PathVariable("isbn") String isbn) {
        return bookService.deleteBook(isbn);
    }

    @GetMapping("/category/{category}")
    @PreAuthorize("isAuthenticated()")
    public List<BookDTO> getBooksByCategory(@PathVariable("category") String category) {
        return bookService.getBooksByCategory(category);
    }

    @GetMapping("/author/{author}")
    @PreAuthorize("isAuthenticated()")
    public List<BookDTO> getBooksByAuthor(@PathVariable("author") String author) {
        return bookService.getBooksByAuthor(author);
    }

    @GetMapping("/title/{title}")
    @PreAuthorize("isAuthenticated()")
    public List<BookDTO> getBooksByTitle(@PathVariable("title") String title) {
        return bookService.getBooksByTitle(title);
    }

    @GetMapping("/quantity/{isbn}")
    @PreAuthorize("isAuthenticated()")
    public Integer getBookQuantity(@PathVariable("isbn") String isbn) {
        return bookService.getBookQuantity(isbn);
    }

}
