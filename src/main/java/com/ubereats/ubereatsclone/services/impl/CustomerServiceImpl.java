package com.ubereats.ubereatsclone.services.impl;

import com.ubereats.ubereatsclone.dtos.CustomerDto;
import com.ubereats.ubereatsclone.entities.Customer;
import com.ubereats.ubereatsclone.entities.FoodItem;
import com.ubereats.ubereatsclone.entities.Order;
import com.ubereats.ubereatsclone.repositories.CustomerRepository;
import com.ubereats.ubereatsclone.repositories.FoodItemRepository;
import com.ubereats.ubereatsclone.repositories.OrderRepository;
import com.ubereats.ubereatsclone.services.CustomerAddressService;
import com.ubereats.ubereatsclone.services.CustomerCartService;
import com.ubereats.ubereatsclone.services.CustomerService;
import com.ubereats.ubereatsclone.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ubereats.ubereatsclone.exceptions.DetailNotFoundException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    private FoodItemRepository foodItemRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    CustomerCartService customerCartService;
    @Autowired
    OrderService orderService;
    @Autowired
    CustomerAddressService customerAddressService;


    @Override
    public CustomerDto createNewCustomer(CustomerDto customerDto) {

        Customer mappedCustomer = this.modelMapper.map(customerDto, Customer.class);
        customerCartService.createNewCart(mappedCustomer.getCustomerCart());
        customerAddressService.createNewAddress(mappedCustomer.getCustomerAddress());
        Customer customerAdded = customerRepository.save(mappedCustomer);

        return this.modelMapper.map(customerAdded, CustomerDto.class);
    }

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
    public CustomerDto updateCustomer(CustomerDto updatedDetails, Long customerId) {
        Customer customer = this.customerRepository.findById(customerId).orElseThrow(() -> new DetailNotFoundException("Customer", "customerId", customerId));

        customer.setEmail(updatedDetails.getEmail());
        customer.setPassword(updatedDetails.getPassword());
        customer.setContactNumber(updatedDetails.getContactNumber());
        customer.setFullName(updatedDetails.getFullName());
        customer.setFavCuisine(updatedDetails.getFavCuisine());

        this.customerRepository.save(customer);

        return this.modelMapper.map(customer, CustomerDto.class);
    }

    @Override
    public void deleteCustomerById(Long customerId) {

        this.customerRepository.deleteById(customerId);

        return;
    }

    @Override
    public void deleteAll() {
        this.customerRepository.deleteAll();
        return;
    }

    @Override
    public CustomerDto getCustomerByEmailId(String emailId) {

        List<Customer> allCustomers = this.customerRepository.findAll();
        for(Customer c : allCustomers) {
            if(c.getEmail().equals(emailId)) {
                return this.modelMapper.map(c, CustomerDto.class);
            }
        }
        return null;
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
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        Customer cus = customerRepository.findById(customerId).orElseThrow(() -> new DetailNotFoundException("Customer", "customerId", customerId));
        Order order = new Order();

        order.setCustomerId(customerId);
        order.setFoodIdsInOrder(new ArrayList<>(cus.getCustomerCart().getFoodIdsInCart()));
        order.setItemCount(cus.getCustomerCart().getFoodIdsInCart().size());
        order.setOrderDate(formatter.format(date));
        order.setTotalPrice(calculateTotalValueOfCart(customerId));

        Order placedOrder = orderService.placeOrder(order);
        customerCartService.emptyCart(cus.getCustomerCart().getCartId());

        return placedOrder;
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


}
