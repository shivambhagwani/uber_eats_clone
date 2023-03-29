package com.ubereats.ubereatsclone.order.repository;

import com.ubereats.ubereatsclone.order.entity.Order;
import com.ubereats.ubereatsclone.order.entity.OrderStatusEnum;
import com.ubereats.ubereatsclone.order.entity.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByRestaurantId(Long restaurantId);
    List<Order> findByCustomerId(Long customerId);

    List<Order> findByRestaurantIdAndOrderStatus(Long restaurantId, OrderStatusEnum orderStatusEnum);

    List<Order> findByRestaurantIdAndOrderType(Long restaurantId, OrderType orderType);

    //To get popular restaurants in the past 24 hours

    @Query(value = "SELECT restaurant_id, COUNT(order_id) AS orderCount " +
            "FROM order_table " +
            "WHERE order_date >= :startDate " +
            "GROUP BY restaurant_id " +
            "ORDER BY orderCount DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Order> findPopularRestaurantsLastDay(@Param("startDate") Date startDate);

    /*
    @Query(value = "SELECT restaurant_id, COUNT(order_id) AS orderCount " +
            "FROM Order o " +
            "WHERE order_date >= :startDate " +
            "GROUP BY restaurant_id " +
            "ORDER BY orderCount DESC " +
            "LIMIT 5", nativeQuery = true)

    @Query(value = "SELECT o.restaurantId, COUNT(o.orderId) AS orderCount " +
            "FROM Order o " +
            "WHERE o.orderDate >= :startDate " +
            "GROUP BY o.restaurantId " +
            "ORDER BY orderCount DESC " +
            "LIMIT 5", nativeQuery = true)

    @Query(value = "SELECT restaurant_id, COUNT(order_id) AS orderCount " +
                "FROM order_table " +
                "WHERE order_date >= :startDate " +
                "GROUP BY restaurant_id " +
                "ORDER BY orderCount DESC " +
                "LIMIT 5", nativeQuery = true)

    @Query(value = "SELECT restaurant_id, COUNT(order_id) AS orderCount " +
            "FROM Order o " +
            "WHERE order_date >= :startDate " +
            "GROUP BY restaurant_id " +
            "ORDER BY orderCount DESC " +
            "LIMIT 5", nativeQuery = true)

    @Query(value = "SELECT o.restaurant_id, COUNT(o.order_id) AS orderCount " +
            "FROM Order o " +
            "WHERE o.order_date >= :startDate " +
            "GROUP BY o.restaurant_id " +
            "ORDER BY orderCount DESC " +
            "LIMIT 5", nativeQuery = true)
    */

}
