package com.ubereats.ubereatsclone.repositories;

import com.ubereats.ubereatsclone.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByRestaurantId(Long restaurantId);

}
