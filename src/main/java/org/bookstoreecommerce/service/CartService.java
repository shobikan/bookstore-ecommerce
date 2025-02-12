package org.bookstoreecommerce.service;

import lombok.RequiredArgsConstructor;
import org.bookstoreecommerce.DTO.CartRequest;
import org.bookstoreecommerce.DTO.CartResponse;
import org.bookstoreecommerce.entity.Book;
import org.bookstoreecommerce.entity.Cart;
import org.bookstoreecommerce.entity.CartItem;
import org.bookstoreecommerce.repository.BookRepository;
import org.bookstoreecommerce.repository.CartItemRepository;
import org.bookstoreecommerce.repository.CartRepository;
import org.bookstoreecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public Boolean addBookToCart(Long userId, CartRequest request) {
        Cart cart = cartRepository.findCartByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found")));
            cartRepository.save(cart);
        }

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setBook(bookRepository.findById(request.getIsbn()).orElseThrow(() -> new IllegalArgumentException("Book not found")));
        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);
        return true;
    }

    public List<CartResponse> getCartItems(Long userId) {
        Long cartId = cartRepository.findCartIdByUserId(userId);
        List<CartItem> cartItems = cartItemRepository.findCartItemsByCartId(cartId);
        List<CartResponse> cartResponses = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            Book book = cartItem.getBook();
            CartResponse cartResponse = CartResponse.builder()
                    .cartItemId(cartItem.getCartItemId())
                    .isbn(book.getIsbn())
                    .title(book.getTitle())
                    .author(book.getAuthor())
                    .price(book.getPrice())
                    .category(book.getCategory())
                    .quantity(cartItem.getQuantity())
                    .build();
            cartResponses.add(cartResponse);
        }
        return cartResponses;
    }

    public Boolean updateCartItemQuantity(Long cartItemId, Integer quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        return true;
    }

    public Boolean deleteCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
        return true;
    }

    public Boolean deleteCart(Long userId) {
        Long cartId = cartRepository.findCartIdByUserId(userId);
        cartItemRepository.deleteCartItemsByCartId(cartId);
        cartRepository.deleteById(cartId);
        return true;
    }
}
