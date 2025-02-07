package org.bookstoreecommerce.controller;

import org.bookstoreecommerce.DTO.BookDTO;
import org.bookstoreecommerce.service.BookService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getBookByIsbn(@PathVariable("isbn") String isbn) {
        return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addBook(@RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.addBook(bookDTO));
    }

    @PutMapping("/update/{isbn}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateBook(@PathVariable("isbn") String isbn, @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.updateBook(isbn, bookDTO));
    }

    @DeleteMapping("/delete/{isbn}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBook(@PathVariable("isbn") String isbn) {
        return ResponseEntity.ok(bookService.deleteBook(isbn));
    }

    @GetMapping("/category/{category}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getBooksByCategory(@PathVariable("category") String category) {
        return ResponseEntity.ok(bookService.getBooksByCategory(category));
    }

    @GetMapping("/author/{author}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getBooksByAuthor(@PathVariable("author") String author) {
        return ResponseEntity.ok(bookService.getBooksByAuthor(author));
    }

    @GetMapping("/title/{title}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getBooksByTitle(@PathVariable("title") String title) {
        return ResponseEntity.ok(bookService.getBooksByTitle(title));
    }

    @GetMapping("/quantity/{isbn}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getBookQuantity(@PathVariable("isbn") String isbn) {
        return ResponseEntity.ok(bookService.getBookQuantity(isbn));
    }

}
