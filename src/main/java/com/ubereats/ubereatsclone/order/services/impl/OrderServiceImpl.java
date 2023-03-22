package com.ubereats.ubereatsclone.order.services.impl;

import com.ubereats.ubereatsclone.order.entity.Order;
import com.ubereats.ubereatsclone.order.repository.OrderRepository;
import com.ubereats.ubereatsclone.order.entity.OrderStatusEnum;
import com.ubereats.ubereatsclone.order.services.OrderService;
import com.ubereats.ubereatsclone.restaurant.entity.RestaurantEmployee;
import com.ubereats.ubereatsclone.restaurant.entity.RestaurantEmployeeEnum;
import com.ubereats.ubereatsclone.util.exceptions.DetailNotFoundException;
import com.ubereats.ubereatsclone.restaurant.services.RestaurantEmployeeService;
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
        Order orderInDB = orderRepository.findById(order.getId()).orElseThrow();
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
                employee.getRestaurant().getId() == order.getRestaurantId() && order.getOrderStatus() != OrderStatusEnum.CANCELLED) {
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
