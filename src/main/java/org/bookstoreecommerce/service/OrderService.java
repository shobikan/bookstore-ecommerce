package org.bookstoreecommerce.service;

import lombok.RequiredArgsConstructor;
import org.bookstoreecommerce.DTO.OrderRequest;
import org.bookstoreecommerce.DTO.OrderResponse;
import org.bookstoreecommerce.entity.Order;
import org.bookstoreecommerce.entity.OrderLine;
import org.bookstoreecommerce.entity.ShippingDetails;
import org.bookstoreecommerce.entity.User;
import org.bookstoreecommerce.enums.OrderStatus;
import org.bookstoreecommerce.enums.PaymentStatus;
import org.bookstoreecommerce.repository.OrderLineRepository;
import org.bookstoreecommerce.repository.OrderRepository;
import org.bookstoreecommerce.repository.ShippingDetailsRepository;
import org.bookstoreecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final BookService bookService;
    private final UserRepository userRepository;
    private final ShippingDetailsRepository shippingDetailsRepository;

    public Boolean createOrder(OrderRequest orderRequest) {
        List<OrderLine> orderLines = orderRequest.getOrderLines();
        if (checkAvailability(orderLines)){
            Order order = new Order();
            order.setTotalAmount(orderRequest.getTotalAmount());
            order.setOrderLines(orderLines);
            User user = userRepository.findById(orderRequest.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            order.setUser(user);
            order.setPaymentStatus(PaymentStatus.PENDING);
            order.setShippingDetails(orderRequest.getShippingDetails());
            order.setOrderStatus(OrderStatus.PROCESSING);
            for (OrderLine orderLine : orderLines) {
                orderLine.setOrder(order);
                bookService.updateBookQuantity(orderLine.getBook().getIsbn(), orderLine.getQuantity());
            }
            orderRepository.save(order);
            return true;
        }
        return false;
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAllOrderResponses();
    }

    public OrderResponse getOrderById(Long orderId) {
        return orderRepository.findOrderResponseById(orderId);
    }

    public ShippingDetails getShippingDetailsByOrderId(Long orderId) {
        return shippingDetailsRepository.findShippingDetailsByOrderId(orderId);
    }

    public List<OrderLine> getOrderLinesByOrderId(Long orderId) {
        return orderLineRepository.findOrderLinesByOrderId(orderId);
    }

    public List<OrderResponse> getOrdersByPaymentStatus(String paymentStatus) {
        return orderRepository.findOrderResponsesByPaymentStatus(paymentStatus);
    }

    public List<OrderResponse> getOrdersByOrderStatus(String orderStatus) {
        return orderRepository.findOrderResponsesByOrderStatus(orderStatus);
    }

    public List<OrderResponse> getOrdersByUserId(Long userId) {
        return orderRepository.findOrderResponsesByUserId(userId);
    }

    public Boolean updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        return true;
    }

    public Boolean updatePaymentDetails(Long orderId, String paymentId, PaymentStatus paymentStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setPaymentId(paymentId);
        order.setPaymentStatus(paymentStatus);
        orderRepository.save(order);
        return true;
    }

    public Boolean deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
        orderLineRepository.deleteOrderLinesByOrderId(orderId);
        return true;
    }

    private Boolean checkAvailability(List<OrderLine> orderLines) {
        for (OrderLine orderLine : orderLines) {
            if (!(bookService.getBookQuantity(orderLine.getBook().getIsbn()) >= orderLine.getQuantity())) {
                throw new RuntimeException("Book with ISBN: " + orderLine.getBook().getIsbn() + " is not available");
            }
        }
        return true;
    }
}
