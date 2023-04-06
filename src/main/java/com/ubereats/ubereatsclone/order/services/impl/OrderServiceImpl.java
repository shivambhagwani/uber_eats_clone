package com.ubereats.ubereatsclone.order.services.impl;

import com.ubereats.ubereatsclone.customer.repository.CustomerRepository;
import com.ubereats.ubereatsclone.customer.services.CustomerService;
import com.ubereats.ubereatsclone.food.entity.FoodItem;
import com.ubereats.ubereatsclone.food.repository.FoodItemRepository;
import com.ubereats.ubereatsclone.order.entity.Order;
import com.ubereats.ubereatsclone.order.entity.OrderType;
import com.ubereats.ubereatsclone.order.repository.OrderRepository;
import com.ubereats.ubereatsclone.order.entity.OrderStatusEnum;
import com.ubereats.ubereatsclone.order.services.OrderService;
import com.ubereats.ubereatsclone.restaurant.services.RestaurantService;
import com.ubereats.ubereatsclone.util.exceptions.DetailNotFoundException;
import com.ubereats.ubereatsclone.restaurant.services.RestaurantEmployeeService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    RestaurantEmployeeService restaurantEmployeeService;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Order placeOrder(Order order) {
        order.setOrderType(OrderType.DELIVERY);
        Order placedOrder = this.orderRepository.save(order);

        try {
            exportInvoice(placedOrder.getOrderId());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }

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

    public void exportInvoice(Long orderId) throws FileNotFoundException, JRException {
            Order order = orderRepository.findById(orderId).orElseThrow();
            List<Long> foodIds = order.getFoodIdsInOrder();
            // Get your map with food name and food cost mapped
            Map<String, Double> foodMap = new HashMap<>();
            for(Long id : foodIds) {
                FoodItem f = foodItemRepository.findById(id).orElseThrow();
                if(foodMap.containsKey(f.getItemName())) {
                    foodMap.put(f.getItemName(), foodMap.get(f.getItemName()) + f.getItemCost());
                } else {
                    foodMap.put(f.getItemName(), f.getItemCost());
                }
            }

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("restaurant", restaurantService.getRestaurantById(order.getRestaurantId()).getRestaurantName());
            parameters.put("customerName", customerRepository.findById(order.getCustomerId()).orElseThrow().getUsername());
            parameters.put("totalItems", order.getItemCount());
            parameters.put("deliveryFee", order.getDeliveryFee());
            parameters.put("totalPrice", String.valueOf(order.getTotalPrice().doubleValue()));
            File file = ResourceUtils.getFile("classpath:invoice.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            // Compile the Jasper Report file
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(foodMap.entrySet()));
            JasperExportManager.exportReportToPdfFile(jasperPrint, "/Users/shivambhagwani/Desktop/invoice_" + orderId + ".pdf");

    }
}
