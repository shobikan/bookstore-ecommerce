package org.bookstoreecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.bookstoreecommerce.DTO.CartRequest;
import org.bookstoreecommerce.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/{userId}/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@PathVariable("userId") Long userId,@RequestBody CartRequest cartRequest) {
        return ResponseEntity.ok(cartService.addBookToCart(userId, cartRequest));
    }

    @GetMapping
    public ResponseEntity<?> getCartItems(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(cartService.getCartItems(userId));
    }

    @PutMapping("/updateCartItemQuantity")
    public ResponseEntity<?> updateCartItemQuantity(@RequestParam("cartItemId") Long cartItemId, @RequestParam("quantity") int quantity) {
        return ResponseEntity.ok(cartService.updateCartItemQuantity(cartItemId, quantity));
    }

    @DeleteMapping("/deleteCartItem")
    public ResponseEntity<?> deleteCartItem(@RequestParam("cartItemId") Long cartItemId) {
        return ResponseEntity.ok(cartService.deleteCartItem(cartItemId));
    }

    @DeleteMapping("/deleteCart")
    public ResponseEntity<?> deleteCart(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(cartService.deleteCart(userId));
    }
}
