package com.ubereats.ubereatsclone.order.services;

import com.ubereats.ubereatsclone.order.entity.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(Order order);

    Order getOrderById(Long id);

    Order updateOrder(Order order);

    List<Order> getRestaurantOrderHistory(Long restaurantId);

    List<Order> getCustomerOrderHistory(Long customerId);

    Order nextOrderStatus(Long orderId);

    List<Order> getNewOrders(Long resId);

    List<Long> getPopularRestaurants();

    Integer newOrderCountForRestaurant(Long resId);
}
