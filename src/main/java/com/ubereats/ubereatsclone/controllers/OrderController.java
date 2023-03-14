package com.ubereats.ubereatsclone.controllers;


import com.ubereats.ubereatsclone.entities.Order;
import com.ubereats.ubereatsclone.entities.RestaurantEmployee;
import com.ubereats.ubereatsclone.services.CustomerService;
import com.ubereats.ubereatsclone.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@Slf4j
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    CustomerService customerService;

    @PostMapping("/{customerId}")
    public Order placeOrder(@PathVariable Long customerId) throws Throwable {
        log.info("Customer {} submitted an order request.", customerId);
        return customerService.submitOrderRequest(customerId);
    }

    @GetMapping("/history/customer/{customerId}")
    public List<Order> orderHistoryOfCustomer(@PathVariable Long customerId) {
        log.info("Customer {} order history requested.", customerId);
        return orderService.getCustomerOrderHistory(customerId);
    }

    @GetMapping("/history/restaurant/{restaurantId}")
    public List<Order> orderHistoryOfRestaurant(@PathVariable Long restaurantId) {
        log.info("Restaurant {} order history requested.", restaurantId);
        return orderService.getRestaurantOrderHistory(restaurantId);
    }

    @PutMapping("/nextStatus/{orderId}")
    public Order updateStatusToNextState(@PathVariable Long orderId, HttpServletRequest request) {
        log.info("Order {} status was attempted to be changed by.", orderId);

        SecurityContext context = (SecurityContext) request.getSession().getAttribute("context");
        if(context != null && context.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            String employeeEmail = context.getAuthentication().getName();
            return orderService.nextOrderStatus(orderId, employeeEmail);

        }

        return null;
    }

    @GetMapping("/newOrders")
    public List<Order> getNewOrders(@RequestBody String restaurantId) {
        log.info("Pending orders from restaurant {} requested.", restaurantId);
        return orderService.getNewOrders(Long.parseLong(restaurantId));
    }

}
