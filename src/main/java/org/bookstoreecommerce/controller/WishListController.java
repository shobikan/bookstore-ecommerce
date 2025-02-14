package org.bookstoreecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.bookstoreecommerce.service.WishListService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/{userId}/wishlist")
@RequiredArgsConstructor
public class WishListController {
    private final WishListService wishListService;

    @PostMapping("/addToWishList")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addToWishList(@PathVariable("userId") Long userId, @RequestParam String bookId) {
        return ResponseEntity.ok(wishListService.addBookToWishList(userId, bookId));
    }

    @DeleteMapping("/removeFromWishList")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> removeFromWishList(@PathVariable("userId") Long userId, @RequestParam String bookId) {
        return ResponseEntity.ok(wishListService.removeBookFromWishList(userId, bookId));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getWishList(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(wishListService.getWishList(userId));
    }

}
