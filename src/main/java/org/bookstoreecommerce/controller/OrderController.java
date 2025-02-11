package org.bookstoreecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.bookstoreecommerce.DTO.OrderRequest;
import org.bookstoreecommerce.enums.OrderStatus;
import org.bookstoreecommerce.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.createOrder(orderRequest));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @GetMapping("/{orderId}/shippingDetails")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getShippingDetails(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getShippingDetailsByOrderId(orderId));
    }

    @GetMapping("/{orderId}/orderLines")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getOrderLines(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderLinesByOrderId(orderId));
    }

    @GetMapping("/{paymentStatus}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getOrdersByPaymentStatus(@PathVariable String paymentStatus) {
        return ResponseEntity.ok(orderService.getOrdersByPaymentStatus(paymentStatus));
    }

    @GetMapping("/{orderStatus}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getOrdersByOrderStatus(@PathVariable String orderStatus) {
        return ResponseEntity.ok(orderService.getOrdersByOrderStatus(orderStatus));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }

    @PutMapping("/{orderId}/updateOrderStatus")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @RequestBody OrderStatus orderStatus) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, orderStatus));
    }

    @DeleteMapping("/{orderId}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.deleteOrder(orderId));
    }
}
