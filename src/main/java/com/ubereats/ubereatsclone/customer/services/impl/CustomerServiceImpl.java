package com.ubereats.ubereatsclone.customer.services.impl;

import com.ubereats.ubereatsclone.customer.dto.CustomerDto;
import com.ubereats.ubereatsclone.customer.entity.Customer;
import com.ubereats.ubereatsclone.customer.services.CustomerAddressService;
import com.ubereats.ubereatsclone.customer.services.CustomerCartService;
import com.ubereats.ubereatsclone.customer.services.CustomerService;
import com.ubereats.ubereatsclone.util.exceptions.UserDetailNotUpdatedException;
import com.ubereats.ubereatsclone.customer.repository.CustomerRepository;
import com.ubereats.ubereatsclone.food.entity.FoodItem;
import com.ubereats.ubereatsclone.order.entity.Order;
import com.ubereats.ubereatsclone.order.services.OrderService;
import com.ubereats.ubereatsclone.food.repository.FoodItemRepository;
import com.ubereats.ubereatsclone.order.entity.OrderStatusEnum;
import com.ubereats.ubereatsclone.restaurant.services.RestaurantService;
import com.ubereats.ubereatsclone.tax.services.TaxService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ubereats.ubereatsclone.util.exceptions.DetailNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    private FoodItemRepository foodItemRepository;
    @Autowired
    private TaxService taxService;
    @Autowired
    CustomerCartService customerCartService;
    @Autowired
    OrderService orderService;
    @Autowired
    CustomerAddressService customerAddressService;
    @Autowired
    RestaurantService restaurantService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public CustomerDto getCustomerById(Long customerId) {
        Customer customer = this.customerRepository.findById(customerId).orElseThrow(() -> new DetailNotFoundException("Customer", "customerId", customerId));
        CustomerDto customerFoundDto = this.modelMapper.map(customer, CustomerDto.class);

        return customerFoundDto;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = this.customerRepository.findAll();
        List<CustomerDto> customerDtos = customers.stream().map(customer -> this.modelMapper.map(customer, CustomerDto.class)).collect(Collectors.toList());
        return customerDtos;
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto updatedDetails) {
        Customer customer = this.customerRepository.findByUsername(updatedDetails.getUsername());
        String passwordOnDB = customer.getPassword();
        if(customer == null) {
            throw new UserDetailNotUpdatedException("Customer Details Not Found.");
        } else if (!passwordEncoder.matches(updatedDetails.getPassword(), passwordOnDB)) {
            throw new UserDetailNotUpdatedException("Password entered is wrong.");
        } else {
            customer.setUsername(updatedDetails.getUsername());
            customer.setContactNumber(updatedDetails.getContactNumber());
            customer.setFullName(updatedDetails.getFullName());
            customer.setFavCuisine(updatedDetails.getFavCuisine());
            this.customerRepository.save(customer);
        }

        return this.modelMapper.map(customer, CustomerDto.class);
    }

    @Transactional
    @Override
    public void deleteCustomerByEmail(String emailId) {
        customerRepository.deleteByUsername(emailId);
        return;
    }

    @Override
    public void deleteAll() {
        this.customerRepository.deleteAll();
        return;
    }

    @Override
    public CustomerDto getCustomerByEmailId(String emailId) {

        Customer cus = customerRepository.findByUsername(emailId);
        CustomerDto mapped = modelMapper.map(cus, CustomerDto.class);
        return mapped;
    }

    @Override
    public Boolean addFoodToCustomerCart(Long customerId, Long foodId) {
        Customer cus = customerRepository.findById(customerId).orElseThrow(() -> new DetailNotFoundException("Customer", "customerId", customerId));

        if(foodItemRepository.existsById(foodId)) {
            cus.getCustomerCart().getFoodIdsInCart().add(foodId);
            customerRepository.save(cus);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean removeFoodFromCustomerCart(Long customerId, Long foodId) {
        Customer cus = customerRepository.findById(customerId).orElseThrow(() -> new DetailNotFoundException("Customer", "customerId", customerId));

        if(foodItemRepository.existsById(foodId) && cus.getCustomerCart().getFoodIdsInCart().contains(foodId)) {
            cus.getCustomerCart().getFoodIdsInCart().remove(foodId);
            customerRepository.save(cus);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public double calculateTotalValueOfCart(Long customerId) {
        Customer cus = customerRepository.findById(customerId).orElseThrow(() -> new DetailNotFoundException("Customer", "customerId", customerId));
        double totalValue = 0;
        if(cus.getCustomerCart().getFoodIdsInCart().isEmpty()) {
            return totalValue;
        } else {
            List<Long> foodIds = cus.getCustomerCart().getFoodIdsInCart();
            for(Long foodId : foodIds) {
                FoodItem food = foodItemRepository.findById(foodId).orElseThrow(() -> new DetailNotFoundException("FoodItem", "foodId", foodId));
                totalValue += food.getItemCost();
            }
        }

        return totalValue;
    }


    @Override
    public Order submitOrderRequest(Long customerId) {
        Customer cus = customerRepository.findById(customerId).orElseThrow(() -> new DetailNotFoundException("Customer", "customerId", customerId));
        String cusPincode = cus.getCustomerAddress().getPincode();
        BigDecimal taxRate = BigDecimal.valueOf(1.00 + taxService.getPincodeTax(cusPincode)/100.00);
        BigDecimal cartTotal = BigDecimal.valueOf(calculateTotalValueOfCart(customerId));
        BigDecimal orderTotal = BigDecimal.valueOf(0);

        Order order = new Order();

        order.setCustomerId(customerId);
        List<Long> foodIds = cus.getCustomerCart().getFoodIdsInCart();
        order.setFoodIdsInOrder(new ArrayList<>(foodIds));

        Long restaurantId = foodItemRepository.findById(foodIds.get(0)).get().getRestaurantId();
        order.setRestaurantId(restaurantId);
        if(cartTotal.compareTo(BigDecimal.valueOf(restaurantService.getRestaurantById(restaurantId).getFreeDeliveryAmount())) == -1) {
            Double deliveryFees = restaurantService.getRestaurantById(restaurantId).getDeliveryFee();
            cartTotal = cartTotal.add(BigDecimal.valueOf(deliveryFees));
            orderTotal = cartTotal.multiply(taxRate);
            order.setDeliveryFee(deliveryFees);
        } else {
            orderTotal = cartTotal.multiply(taxRate);
            order.setDeliveryFee(0.0);
        }
        order.setItemCount(cus.getCustomerCart().getFoodIdsInCart().size());
        order.setTotalPrice(orderTotal);
        order.setOrderStatus(OrderStatusEnum.SUBMITTED);

        Order placedOrder = orderService.placeOrder(order);
        customerCartService.emptyCart(cus.getCustomerCart().getCartId());

        return placedOrder;
    }

    @Override
    public Order cancelOrder(Long orderId, String customerEmail) {
        Order order = orderService.getOrderById(orderId);
        String customerWhoPlacedOrder = getCustomerById(order.getCustomerId()).getUsername();
        Order cancelledOrder = null;

        if(customerEmail.equals(customerWhoPlacedOrder) && order.getOrderStatus() == OrderStatusEnum.SUBMITTED) {
            order.setOrderStatus(OrderStatusEnum.CANCELLED);
            cancelledOrder = orderService.updateOrder(order);
        }

        return cancelledOrder;
    }

    @Override
    public void addRestaurantToFav(String email, Long restaurantId) {
        Customer cus = customerRepository.findByUsername(email);
        if(restaurantService.restaurantExists(restaurantId)) {
            cus.getFavouriteRestaurants().add(restaurantId);
            customerRepository.save(cus);
        } else {
            throw new DetailNotFoundException("Restaurant", "RestaurantID", restaurantId);
        }
    }

}
