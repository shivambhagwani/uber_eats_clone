package com.ubereats.ubereatsclone.services.impl;

import com.ubereats.ubereatsclone.entities.Order;
import com.ubereats.ubereatsclone.repositories.OrderRepository;
import com.ubereats.ubereatsclone.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Order placeOrder(Order order) {
        Order placedOrder = this.orderRepository.save(order);
        return placedOrder;
    }
}
