package com.ubereats.ubereatsclone.repositories;

import com.ubereats.ubereatsclone.entities.Order;
import com.ubereats.ubereatsclone.entities.OrderStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByRestaurantId(Long restaurantId);
    List<Order> findByCustomerId(Long customerId);

    List<Order> findByRestaurantIdAndOrderStatus(Long restaurantId, OrderStatusEnum orderStatusEnum);

}
