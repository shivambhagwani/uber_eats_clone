package com.ubereats.ubereatsclone.services.impl;

import com.ubereats.ubereatsclone.entities.Order;
import com.ubereats.ubereatsclone.entities.OrderStatusEnum;
import com.ubereats.ubereatsclone.entities.RestaurantEmployee;
import com.ubereats.ubereatsclone.entities.RestaurantEmployeeEnum;
import com.ubereats.ubereatsclone.exceptions.DetailNotFoundException;
import com.ubereats.ubereatsclone.repositories.OrderRepository;
import com.ubereats.ubereatsclone.services.OrderService;
import com.ubereats.ubereatsclone.services.RestaurantEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    RestaurantEmployeeService restaurantEmployeeService;

    @Override
    public Order placeOrder(Order order) {
        Order placedOrder = this.orderRepository.save(order);
        return placedOrder;
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    @Override
    public Order updateOrder(Order order) {
        Order orderInDB = orderRepository.findById(order.getOrderId()).orElseThrow();
        orderInDB.setOrderStatus(order.getOrderStatus());
        orderInDB.setOrderDate(order.getOrderDate());
        orderInDB.setFoodIdsInOrder(order.getFoodIdsInOrder());
        orderInDB.setTotalPrice(order.getTotalPrice());

        Order updatedOrder = orderRepository.save(orderInDB);

        return updatedOrder;
    }

    @Override
    public List<Order> getRestaurantOrderHistory(Long restaurantId) {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        return orders;
    }

    @Override
    public List<Order> getCustomerOrderHistory(Long customerId) {

        return  orderRepository.findByCustomerId(customerId);
    }

    @Override
    public Order nextOrderStatus(Long orderId, String loggedInEmployeeEmail) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new DetailNotFoundException("Order", "orderId", orderId));
        RestaurantEmployee employee = restaurantEmployeeService.getEmployeeByEmail(loggedInEmployeeEmail);

        if(employee.getJobRole() == RestaurantEmployeeEnum.ADMIN &&
                employee.getRestaurant().getRestaurantId() == order.getRestaurantId() && order.getOrderStatus() != OrderStatusEnum.CANCELLED) {
            order.setOrderStatus(order.getOrderStatus().next());
        }

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getNewOrders(Long resId) {
        List<Order> orders = this.orderRepository.findByRestaurantIdAndOrderStatus(resId, OrderStatusEnum.SUBMITTED);
        return orders;
    }
}
