package com.ubereats.ubereatsclone.services.impl;

import com.ubereats.ubereatsclone.entities.Order;
import com.ubereats.ubereatsclone.repositories.OrderRepository;
import com.ubereats.ubereatsclone.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Order placeOrder(Order order) {
        Order placedOrder = this.orderRepository.save(order);
        return placedOrder;
    }

    @Override
    public List<Order> getRestaurantOrderHistory(Long resId) {
        List<Order> orders = this.orderRepository.findAll();

        List<Order> restaurantOrder = new ArrayList<>();

        for(Order o : orders) {
            if(o.getRestaurantId() == resId)
                restaurantOrder.add(o);
        }

        return  restaurantOrder;
    }

    @Override
    public List<Order> getCustomerOrderHistory(Long customerId) {
        List<Order> orders = this.orderRepository.findAll();

        List<Order> customerOrder = new ArrayList<>();

        for(Order o : orders) {
            if(o.getCustomerId() == customerId)
                customerOrder.add(o);
        }

        return  customerOrder;
    }
}
