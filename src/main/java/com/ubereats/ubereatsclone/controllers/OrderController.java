package com.ubereats.ubereatsclone.controllers;


import com.ubereats.ubereatsclone.entities.Order;
import com.ubereats.ubereatsclone.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/{customerId}")
    public Order placeOrder(@PathVariable Long customerId) {
        return customerService.submitOrderRequest(customerId);
    }

    @GetMapping("/history/{customerId}")
    public List<Order> orderHistoryOfCustomer(@PathVariable Long customerId) {
        return customerService.getCustomerOrderHistory(customerId);
    }

}
