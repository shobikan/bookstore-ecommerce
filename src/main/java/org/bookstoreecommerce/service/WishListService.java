package org.bookstoreecommerce.service;

import lombok.RequiredArgsConstructor;
import org.bookstoreecommerce.DTO.WishListResponse;
import org.bookstoreecommerce.entity.Book;
import org.bookstoreecommerce.entity.User;
import org.bookstoreecommerce.entity.WishList;
import org.bookstoreecommerce.repository.BookRepository;
import org.bookstoreecommerce.repository.UserRepository;
import org.bookstoreecommerce.repository.WishListRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final BookRepository bookRepository;
    private final WishListRepository wishListRepository;
    private final UserRepository userRepository;


    public Boolean addBookToWishList(Long userId, String bookId) {
        WishList wishList = wishListRepository.findWishListByUserId(userId)
                .orElseGet(() -> {
                    WishList newWishList = new WishList();
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new IllegalArgumentException("User not found"));
                    newWishList.setUser(user);
                    return wishListRepository.save(newWishList);
                });

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        if (!wishList.getBooks().contains(book)) {
            wishList.getBooks().add(book);
            wishListRepository.save(wishList);
        }
        return true;
    }

    public Boolean removeBookFromWishList(Long userId, String bookId) {
        WishList wishList = wishListRepository.findWishListByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("WishList not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        wishList.getBooks().remove(book);
        wishListRepository.save(wishList);
        return true;
    }

    public List<WishListResponse> getWishList(Long userId) {
        WishList wishList = wishListRepository.findWishListByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("WishList not found"));

        List<Book> books = wishList.getBooks();
        List<WishListResponse> wishListResponses = new ArrayList<>();
        for (Book book : books) {
            wishListResponses.add(convertToDTO(book));
        }
        return wishListResponses;
    }

    private WishListResponse convertToDTO(Book book) {
        return WishListResponse.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice())
                .category(book.getCategory())
                .build();
    }
}
