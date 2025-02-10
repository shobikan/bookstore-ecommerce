package org.bookstoreecommerce.service;

import lombok.RequiredArgsConstructor;
import org.bookstoreecommerce.DTO.OrderRequest;
import org.bookstoreecommerce.DTO.OrderResponse;
import org.bookstoreecommerce.entity.Order;
import org.bookstoreecommerce.entity.OrderLine;
import org.bookstoreecommerce.entity.User;
import org.bookstoreecommerce.enums.OrderStatus;
import org.bookstoreecommerce.enums.PaymentStatus;
import org.bookstoreecommerce.repository.OrderLineRepository;
import org.bookstoreecommerce.repository.OrderRepository;
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

    private Boolean checkAvailability(List<OrderLine> orderLines) {
        for (OrderLine orderLine : orderLines) {
            if (!(bookService.getBookQuantity(orderLine.getBook().getIsbn()) >= orderLine.getQuantity())) {
                throw new RuntimeException("Book with ISBN: " + orderLine.getBook().getIsbn() + " is not available");
            }
        }
        return true;
    }
}
