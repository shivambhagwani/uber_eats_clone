package com.ubereats.ubereatsclone.services.impl;

import com.ubereats.ubereatsclone.dtos.CustomerDto;
import com.ubereats.ubereatsclone.entities.*;
import com.ubereats.ubereatsclone.exceptions.LoginFailedException;
import com.ubereats.ubereatsclone.exceptions.UserDetailNotUpdatedException;
import com.ubereats.ubereatsclone.exceptions.UserAlreadyExistsException;
import com.ubereats.ubereatsclone.repositories.CustomerRepository;
import com.ubereats.ubereatsclone.repositories.FoodItemRepository;
import com.ubereats.ubereatsclone.services.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ubereats.ubereatsclone.exceptions.DetailNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    PasswordEncoder passwordEncoder;

//    @Autowired
//    AuthenticationManager authenticationManager;



    @Override
    public CustomerDto createNewCustomer(CustomerDto customerDto) {

        if(customerRepository.findByEmail(customerDto.getEmail()) != null) {
            throw new UserAlreadyExistsException("User with email :" + customerDto.getEmail() +" already exists.");
        }

        Customer mappedCustomer = this.modelMapper.map(customerDto, Customer.class);
        mappedCustomer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
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
    public CustomerDto updateCustomer(CustomerDto updatedDetails) {
        Customer customer = this.customerRepository.findByEmail(updatedDetails.getEmail());
        String passwordOnDB = customer.getPassword();
        if(customer == null) {
            throw new UserDetailNotUpdatedException("Customer Details Not Found.");
        } else if (passwordEncoder.matches(updatedDetails.getPassword(), passwordOnDB) == false) {
            throw new UserDetailNotUpdatedException("Password entered is wrong.");
        } else {
            customer.setEmail(updatedDetails.getEmail());
            customer.setContactNumber(updatedDetails.getContactNumber());
            customer.setFullName(updatedDetails.getFullName());
            customer.setFavCuisine(updatedDetails.getFavCuisine());
            this.customerRepository.save(customer);
        }

        return this.modelMapper.map(customer, CustomerDto.class);
    }

    @Override
    public boolean login(String email, String password) {
        Customer customer = customerRepository.findByEmail(email);
        if(customer == null) {
            throw new LoginFailedException("Please check email id and try again.");
        } else if (passwordEncoder.matches(password, customer.getPassword())){
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication authentication = new TestingAuthenticationToken(email, password);
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
            return true;
        }
        return false;
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

        Customer cus = customerRepository.findByEmail(emailId);
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
    public Order submitOrderRequest(Long customerId) throws Throwable {
        Customer cus = customerRepository.findById(customerId).orElseThrow(() -> new DetailNotFoundException("Customer", "customerId", customerId));
        String cusPincode = cus.getCustomerAddress().getPincode();
        double taxRate = (double)(1.00 + (double)taxService.getPincodeTax(cusPincode)/100.00);
        double cartTotal = calculateTotalValueOfCart(customerId);
        double orderTotal = cartTotal * taxRate;

        Order order = new Order();

        order.setCustomerId(customerId);
        List<Long> foodIds = cus.getCustomerCart().getFoodIdsInCart();
        order.setFoodIdsInOrder(new ArrayList<>(foodIds));

        Long restaurantId = foodItemRepository.findById(foodIds.get(0)).get().getRestaurantId();
        order.setRestaurantId(restaurantId);

        order.setItemCount(cus.getCustomerCart().getFoodIdsInCart().size());
        order.setTotalPrice(orderTotal);
        order.setOrderStatus(OrderStatusEnum.SUBMITTED);

        Order placedOrder = orderService.placeOrder(order);
        customerCartService.emptyCart(cus.getCustomerCart().getCartId());

        return placedOrder;
    }

}
