package com.ubereats.ubereatsclone.order.controller;

import com.ubereats.ubereatsclone.customer.services.CustomerService;
import com.ubereats.ubereatsclone.order.entity.Order;
import com.ubereats.ubereatsclone.order.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PostMapping("/placeOrder")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public Order placeOrder() {
        Long customerId = fetchIdFromHeader();
        log.info("Customer {} submitted an order request.", customerId);
        return customerService.submitOrderRequest(customerId);
    }

    @PutMapping("/cancelOrder/{orderId}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {

        log.info("Order is being cancelled. Order id - {}", orderId);
        String customerEmail = fetchEmailFromHeader();

        Order cancelledOrder = customerService.cancelOrder(orderId, customerEmail);
        if(cancelledOrder != null)
                return new ResponseEntity<>(cancelledOrder, HttpStatus.ACCEPTED);

        return new ResponseEntity<>("Order can not be cancelled. Order status might have changed", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/history/customer/{customerId}")
    public List<Order> orderHistoryOfCustomer(@PathVariable Long customerId) {
        log.info("Customer {} order history requested.", customerId);
        return orderService.getCustomerOrderHistory(customerId);
    }


    //TODO - Two functions below to be secured for restaurant admin access.
    @GetMapping("/history/restaurant/{restaurantId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Order> orderHistoryOfRestaurant(@PathVariable Long restaurantId) {
        log.info("Restaurant {} order history requested.", restaurantId);
        return orderService.getRestaurantOrderHistory(restaurantId);
    }

    @PutMapping("/nextStatus/{orderId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Order updateStatusToNextState(@PathVariable Long orderId) {
        log.info("Order {} status was attempted to be changed by.", orderId);
        String employeeEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return orderService.nextOrderStatus(orderId);
    }

    //TODO - Function below to be secured for restaurant admin and chef access.
    @GetMapping("/newOrders")
    public List<Order> getNewOrders(@RequestBody String restaurantId) {
        log.info("Pending orders from restaurant {} requested.", restaurantId);
        //test top restaurants in past 24 hrs
        return orderService.getNewOrders(Long.parseLong(restaurantId));
    }

    private Long fetchIdFromHeader() {
        return customerService.getCustomerByEmailId(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
    }

    private String fetchEmailFromHeader() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
