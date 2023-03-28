package com.ubereats.ubereatsclone.order.repository;

import com.ubereats.ubereatsclone.order.entity.Order;
import com.ubereats.ubereatsclone.order.entity.OrderStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByRestaurantId(Long restaurantId);
    List<Order> findByCustomerId(Long customerId);

    List<Order> findByRestaurantIdAndOrderStatus(Long restaurantId, OrderStatusEnum orderStatusEnum);

    //To get popular restaurants in the past 24 hours
    @Query(value = "SELECT o.restaurantId, COUNT(*) AS orderCount " +
            "FROM Order o " +
            "WHERE o.orderDate >= CURRENT_DATE - 1 " +
            "GROUP BY o.restaurantId " +
            "ORDER BY orderCount DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> findPopularRestaurantsLastDay();

}
