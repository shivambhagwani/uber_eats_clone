package com.ubereats.ubereatsclone.customer.services;

import com.ubereats.ubereatsclone.customer.dto.CustomerDto;
import com.ubereats.ubereatsclone.order.entity.Order;
import org.springframework.security.core.context.SecurityContext;

import java.util.List;

public interface CustomerService {

    //Adding new customer to database
    public CustomerDto registerCustomer(CustomerDto customerDto);

    //Getting single customer detail from database by ID.
    public CustomerDto getCustomerById(Long customerId);

    //Getting all customers
    public List<CustomerDto> getAllCustomers();

    //Update customer detail
    public CustomerDto updateCustomer(CustomerDto updatedDetails);

    public void deleteCustomerByEmail(String emailId);

    //delete all
    public void deleteAll();

    //find using email ID.
    public CustomerDto getCustomerByEmailId(String emailId);

    //Adding to Cart and Removing from Cart APIs
    public Boolean addFoodToCustomerCart(Long customerId, Long foodId);

    public Boolean removeFoodFromCustomerCart(Long customerId, Long foodId);

    public double calculateTotalValueOfCart(Long customerId);

    Order submitOrderRequest(Long customerId) throws Throwable;

    Order cancelOrder(Long orderId, String customerEmail);

    void addRestaurantToFav(String email, Long restaurantId);

}
