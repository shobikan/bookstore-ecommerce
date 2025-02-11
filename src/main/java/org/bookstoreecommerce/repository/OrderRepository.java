package org.bookstoreecommerce.repository;

import org.bookstoreecommerce.DTO.OrderResponse;
import org.bookstoreecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT new org.bookstoreecommerce.DTO.OrderResponse(o.orderId, o.totalAmount, o.createdDate, o.user.userId, o.paymentId, o.paymentStatus,  o.orderStatus) " +
            "FROM Order o WHERE o.orderId = :orderId")
    OrderResponse findOrderResponseById(@Param("orderId") Long orderId);

    @Query("SELECT new org.bookstoreecommerce.DTO.OrderResponse(o.orderId, o.totalAmount, o.createdDate, o.user.userId, o.paymentId, o.paymentStatus, o.orderStatus) " +
            "FROM Order o")
    List<OrderResponse> findAllOrderResponses();

    @Query("SELECT new org.bookstoreecommerce.DTO.OrderResponse(o.orderId, o.totalAmount, o.createdDate, o.user.userId, o.paymentId, o.paymentStatus,  o.orderStatus) " +
            "FROM Order o WHERE o.paymentStatus = :paymentStatus")
    List<OrderResponse> findOrderResponsesByPaymentStatus(@Param("paymentStatus") String paymentStatus);

    @Query("SELECT new org.bookstoreecommerce.DTO.OrderResponse(o.orderId, o.totalAmount, o.createdDate, o.user.userId, o.paymentId, o.paymentStatus,  o.orderStatus) " +
            "FROM Order o WHERE o.orderStatus = :orderStatus")
    List<OrderResponse> findOrderResponsesByOrderStatus(@Param("orderStatus") String orderStatus);

    @Query("SELECT new org.bookstoreecommerce.DTO.OrderResponse(o.orderId, o.totalAmount, o.createdDate, o.user.userId, o.paymentId, o.paymentStatus,  o.orderStatus) " +
            "FROM Order o WHERE o.user.userId = :userId")
    List<OrderResponse> findOrderResponsesByUserId(@Param("userId") Long userId);

}
