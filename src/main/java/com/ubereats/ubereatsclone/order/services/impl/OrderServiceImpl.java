package com.ubereats.ubereatsclone.order.services.impl;

import com.ubereats.ubereatsclone.order.entity.Order;
import com.ubereats.ubereatsclone.order.entity.OrderType;
import com.ubereats.ubereatsclone.order.repository.OrderRepository;
import com.ubereats.ubereatsclone.order.entity.OrderStatusEnum;
import com.ubereats.ubereatsclone.order.services.OrderService;
import com.ubereats.ubereatsclone.restaurant.entity.RestaurantEmployee;
import com.ubereats.ubereatsclone.restaurant.entity.RestaurantEmployeeEnum;
import com.ubereats.ubereatsclone.util.exceptions.DetailNotFoundException;
import com.ubereats.ubereatsclone.restaurant.services.RestaurantEmployeeService;
import org.hibernate.type.LocalDateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    RestaurantEmployeeService restaurantEmployeeService;

    @Override
    public Order placeOrder(Order order) {
        order.setOrderType(OrderType.DELIVERY);
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
        Date startDate = new Date();
        Date oneDayBefore = new Date(startDate.getTime() - Duration.ofDays(1).toMillis());
        List<Order> populars = orderRepository.findPopularRestaurantsLastDay(oneDayBefore);
        return orders;
    }

    @Override
    public List<Order> getCustomerOrderHistory(Long customerId) {

        return  orderRepository.findByCustomerId(customerId);
    }

    @Override
    public Order nextOrderStatus(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new DetailNotFoundException("Order", "orderId", orderId));
        order.setOrderStatus(order.getOrderStatus().next());


        return orderRepository.save(order);
    }

    @Override
    public List<Order> getNewOrders(Long resId) {
        List<Order> orders = this.orderRepository.findByRestaurantIdAndOrderStatus(resId, OrderStatusEnum.SUBMITTED);
        return orders;
    }

    @Override
    public Integer newOrderCountForRestaurant(Long resId) {
        return this.orderRepository.findByRestaurantIdAndOrderStatus(resId, OrderStatusEnum.SUBMITTED).size();
    }
}
