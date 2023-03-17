package com.ubereats.ubereatsclone.customer.services.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.ubereats.ubereatsclone.customer.dto.CustomerDto;
import com.ubereats.ubereatsclone.customer.entity.Customer;
import com.ubereats.ubereatsclone.customer.services.CustomerAddressService;
import com.ubereats.ubereatsclone.customer.services.CustomerCartService;
import com.ubereats.ubereatsclone.customer.services.CustomerService;
import com.ubereats.ubereatsclone.exceptions.LoginFailedException;
import com.ubereats.ubereatsclone.exceptions.UserDetailNotUpdatedException;
import com.ubereats.ubereatsclone.exceptions.UserAlreadyExistsException;
import com.ubereats.ubereatsclone.customer.repository.CustomerRepository;
import com.ubereats.ubereatsclone.food.entity.FoodItem;
import com.ubereats.ubereatsclone.order.entity.Order;
import com.ubereats.ubereatsclone.order.services.OrderService;
import com.ubereats.ubereatsclone.food.repository.FoodItemRepository;
import com.ubereats.ubereatsclone.order.entity.OrderStatusEnum;
import com.ubereats.ubereatsclone.tax.services.TaxService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ubereats.ubereatsclone.exceptions.DetailNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        } else if (!passwordEncoder.matches(updatedDetails.getPassword(), passwordOnDB)) {
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
    public SecurityContext login(String email, String password) {
        Customer customer = customerRepository.findByEmail(email);
        if(customer == null) {
            throw new LoginFailedException("Please check email id and try again.");
        } else if (passwordEncoder.matches(password, customer.getPassword())){
            List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
            Authentication authentication = new UsernamePasswordAuthenticationToken(email, customer.getPassword(), grantedAuths);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return SecurityContextHolder.getContext();
        }
        return null;
    }

    @Transactional
    @Override
    public void deleteCustomerByEmail(String emailId) {
        customerRepository.deleteByEmail(emailId);
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


    @Value("${stripe.test_key}")
    String stripeKey;

    @Override
    public Order submitOrderRequest(Long customerId) throws Throwable {
        Customer cus = customerRepository.findById(customerId).orElseThrow(() -> new DetailNotFoundException("Customer", "customerId", customerId));
        String cusPincode = cus.getCustomerAddress().getPincode();
        BigDecimal taxRate = BigDecimal.valueOf(1.00 + taxService.getPincodeTax(cusPincode)/100.00);
        BigDecimal cartTotal = BigDecimal.valueOf(calculateTotalValueOfCart(customerId));
        BigDecimal orderTotal = cartTotal.multiply(taxRate);

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
        HashMap test = generatePaymentIntent(15L, cus);

        return placedOrder;
    }

    public HashMap<String, String> generatePaymentIntent(Long amount, Customer customer) throws StripeException {
        Stripe.apiKey = stripeKey;
        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("email", customer.getEmail());
        com.stripe.model.Customer cus = com.stripe.model.Customer.create(customerParams);

        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                .setCurrency("usd")
                .setAmount((long) (amount * 100))
                .setDescription("Payment for an order from customer with email : " + customer.getEmail())
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                .setEnabled(true)
                                .build()
                )
                .build();
        PaymentIntent paymentIntent = PaymentIntent.create(createParams);

        HashMap<String, String> clientSecretResponse = new HashMap<>();
        clientSecretResponse.put("clientSecret", paymentIntent.getClientSecret());

        return clientSecretResponse;
    }

    @Override
    public Order cancelOrder(Long orderId, String customerEmail) {
        Order order = orderService.getOrderById(orderId);
        String customerWhoPlacedOrder = getCustomerById(order.getCustomerId()).getEmail();
        Order cancelledOrder = null;

        if(customerEmail.equals(customerWhoPlacedOrder) && order.getOrderStatus() == OrderStatusEnum.SUBMITTED) {
            order.setOrderStatus(OrderStatusEnum.CANCELLED);
            cancelledOrder = orderService.updateOrder(order);
        }

        return cancelledOrder;
    }

}
