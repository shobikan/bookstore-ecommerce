package org.bookstoreecommerce.repository;

import jakarta.transaction.Transactional;
import org.bookstoreecommerce.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    @Query("SELECT ol FROM OrderLine ol WHERE ol.order.orderId = :orderId")
    List<OrderLine> findByOrder(Long orderId);

    @Modifying
    @Transactional
    @Query("DELETE FROM OrderLine ol WHERE ol.order.orderId = :orderId")
    void deleteByOrderId(Long orderId);
}
