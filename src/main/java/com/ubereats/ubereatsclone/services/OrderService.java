package com.ubereats.ubereatsclone.services;

import com.ubereats.ubereatsclone.entities.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(Order order);

    List<Order> getRestaurantOrderHistory(Long resId);

    List<Order> getCustomerOrderHistory(Long customerId);

    Order nextOrderStatus(Long orderId, Long empId);
}
