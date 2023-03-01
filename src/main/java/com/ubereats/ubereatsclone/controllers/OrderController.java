package com.ubereats.ubereatsclone.controllers;


import com.ubereats.ubereatsclone.entities.Order;
import com.ubereats.ubereatsclone.services.CustomerService;
import com.ubereats.ubereatsclone.services.OrderService;
import com.ubereats.ubereatsclone.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    CustomerService customerService;

    @PostMapping("/{customerId}")
    public Order placeOrder(@PathVariable Long customerId) {
        return customerService.submitOrderRequest(customerId);
    }

    @GetMapping("/history/customer/{customerId}")
    public List<Order> orderHistoryOfCustomer(@PathVariable Long customerId) {
        return orderService.getCustomerOrderHistory(customerId);
    }

    @GetMapping("/history/restaurant/{restaurantId}")
    public List<Order> orderHistoryOfRestaurant(@PathVariable Long restaurantId) {
        return orderService.getRestaurantOrderHistory(restaurantId);
    }

}
