package org.bookstoreecommerce.repository;

import org.bookstoreecommerce.entity.ShippingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingDetailsRepository extends JpaRepository<ShippingDetails, Long> {
    @Query("SELECT sd FROM ShippingDetails sd WHERE sd.order.orderId = :orderId")
    ShippingDetails findShippingDetailsByOrderId(Long orderId);

}
