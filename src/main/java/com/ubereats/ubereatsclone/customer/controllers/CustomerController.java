package com.ubereats.ubereatsclone.customer.controllers;

import com.ubereats.ubereatsclone.customer.authorization.service.JwtService;
import com.ubereats.ubereatsclone.customer.dto.CustomerDto;
import com.ubereats.ubereatsclone.customer.authorization.dto.CustomerAuthRequestDTO;
import com.ubereats.ubereatsclone.customer.services.CustomerAuthService;
import com.ubereats.ubereatsclone.customer.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
@Slf4j
public class CustomerController {


    final
    CustomerService customerService;

    final
    CustomerAuthService customerAuthService;

    final
    AuthenticationManager authenticationManager;

    final
    JwtService jwtService;

    public CustomerController(CustomerAuthService customerAuthService, AuthenticationManager authenticationManager, CustomerService customerService, JwtService jwtService) {
        this.customerAuthService = customerAuthService;
        this.authenticationManager = authenticationManager;
        this.customerService = customerService;
        this.jwtService = jwtService;
    }

    //Add new customer.
    @PostMapping("/register")
    public ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerDto customerDto) {
        log.info("Creating new customer.");
        CustomerDto addedCustomerDto = this.customerAuthService.registerCustomer(customerDto);

        return new ResponseEntity<>(addedCustomerDto, HttpStatus.CREATED);
    }


    @GetMapping("/{customerId}")
    public CustomerDto getCustomerById(@PathVariable Long customerId) {
        log.info("Getting customer with id {}.", customerId);
        try {
            return this.customerService.getCustomerById(customerId);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/")
    public List<CustomerDto> getAllCustomers() {
        log.info("Customer list requested.");
        return this.customerService.getAllCustomers();
    }

    @PutMapping("/")
    public ResponseEntity<CustomerDto> updateCustomerById(@RequestBody CustomerDto updatedDetails) {
        log.info("Customer with customer email {} was attempted to be updated.", updatedDetails.getEmail());
        CustomerDto updatedCustomer = this.customerService.updateCustomer(updatedDetails);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteByEmail(@RequestBody String emailId, HttpServletRequest request) {
        log.info("Attempting to delete customer with email-id = {}", emailId);

        /*
         * Checking if the customer logged-in is same as the email-id they are trying to delete.
         */
        SecurityContext context = (SecurityContext) request.getSession().getAttribute("context");
        if(context == null) {
            return ResponseEntity.ok(Map.of("message", "Log-in required."));
        }
        if(context.getAuthentication().getPrincipal().toString().equals(emailId)) {
            String email = context.getAuthentication().getName();
            customerService.deleteCustomerByEmail(email);
            request.getSession().invalidate();
        } else {
            return ResponseEntity.ok(Map.of("message", "Please validate your email-id again."));
        }
        return ResponseEntity.ok(Map.of("message", "Customer deleted by email."));
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAll() {
        log.info("Customers erased.");
        this.customerService.deleteAll();
        return ResponseEntity.ok(Map.of("message", "Customers deleted."));
    }

    @GetMapping("email/{emailId}")
    public CustomerDto findByEmailID(@PathVariable String emailId) {
        log.info("Customer with email id {} was searched for.", emailId);
        return this.customerService.getCustomerByEmailId(emailId);
    }

    @PutMapping("/food/{foodId}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public Boolean addFoodToCustomerCart(@PathVariable Long foodId) {
        Long customerId = fetchIdFromHeader();
        log.info("Customer {} added food {} to the cart.", customerId, foodId);
        return this.customerService.addFoodToCustomerCart(customerId, foodId);
    }

    @GetMapping("/cartTotal")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public double getCartTotal() {
        Long customerId = fetchIdFromHeader();
        log.info("Customer {} cart total requested.", customerId);
        return this.customerService.calculateTotalValueOfCart(customerId);
    }

    @PutMapping("/{customerId}/deleteFood/{foodId}")
    public Boolean deleteFoodFromCustomerCart(@PathVariable Long customerId, @PathVariable Long foodId) {
        log.info("Food {} was deleted from the cart of customer {}", foodId, customerId);
        return this.customerService.removeFoodFromCustomerCart(customerId, foodId);
    }

    @PutMapping("/addToFav/{restaurantId}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<String> addRestaurantToFav(@PathVariable Long restaurantId) {
        log.info("Adding restaurant to favourite");

        SecurityContext context = SecurityContextHolder.getContext();
        if(context != null) {
            String email = context.getAuthentication().getName();
            customerService.addRestaurantToFav(email, restaurantId);
            return new ResponseEntity<>("Restaurant added to fav for customer " + email, HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>("Could not add due to some issue. Customer login required.", HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("/authenticate")
    public String customerLogin(@RequestBody CustomerAuthRequestDTO credentials) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if(authentication.isAuthenticated()) {
            return jwtService.generateToken(credentials.getEmail());
        } else {
            throw new UsernameNotFoundException("Username not found.");
        }
    }

    private Long fetchIdFromHeader() {
        return customerService.getCustomerByEmailId(SecurityContextHolder.getContext().getAuthentication().getName()).getCustomerId();
    }
}
