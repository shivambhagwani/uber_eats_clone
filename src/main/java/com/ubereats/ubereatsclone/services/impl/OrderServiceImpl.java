package com.ubereats.ubereatsclone.services.impl;

import com.ubereats.ubereatsclone.entities.Order;
import com.ubereats.ubereatsclone.entities.RestaurantEmployee;
import com.ubereats.ubereatsclone.entities.RestaurantEmployeeEnum;
import com.ubereats.ubereatsclone.exceptions.DetailNotFoundException;
import com.ubereats.ubereatsclone.repositories.OrderRepository;
import com.ubereats.ubereatsclone.repositories.RestaurantEmployeeRepository;
import com.ubereats.ubereatsclone.services.OrderService;
import com.ubereats.ubereatsclone.services.RestaurantEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    RestaurantEmployeeService restaurantEmployeeService;

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

    @Override
    public Order nextOrderStatus(Long orderId, Long empId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new DetailNotFoundException("Order", "orderId", orderId));

        Long resId = order.getRestaurantId();
        List<RestaurantEmployee> employees = restaurantEmployeeService.getAllEmployees(resId);
        RestaurantEmployee employee = restaurantEmployeeService.getEmployeeById(empId);

        if(employees.contains(employee) &&
                employee.getJob_role() == RestaurantEmployeeEnum.ADMIN &&
                employee.getRestaurant().getRestaurantId() == order.getRestaurantId()) {
            order.setOrderStatus(order.getOrderStatus().next());
        }

        return orderRepository.save(order);
    }
}
