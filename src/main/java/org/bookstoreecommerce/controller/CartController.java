package org.bookstoreecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.bookstoreecommerce.DTO.CartRequest;
import org.bookstoreecommerce.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/{userId}/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addToCart(@PathVariable("userId") Long userId,@RequestBody CartRequest cartRequest) {
        return ResponseEntity.ok(cartService.addBookToCart(userId, cartRequest));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getCartItems(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(cartService.getCartItems(userId));
    }

    @PutMapping("/updateCartItemQuantity")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateCartItemQuantity(@RequestParam("cartItemId") Long cartItemId, @RequestParam("quantity") int quantity) {
        return ResponseEntity.ok(cartService.updateCartItemQuantity(cartItemId, quantity));
    }

    @DeleteMapping("/deleteCartItem")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteCartItem(@RequestParam("cartItemId") Long cartItemId) {
        return ResponseEntity.ok(cartService.deleteCartItem(cartItemId));
    }

    @DeleteMapping("/deleteCart")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteCart(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(cartService.deleteCart(userId));
    }
}
