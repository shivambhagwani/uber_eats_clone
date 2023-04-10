package com.ubereats.ubereatsclone.customer.controllers;

import com.ubereats.ubereatsclone.authentication.classes.LoginCredentials;
import com.ubereats.ubereatsclone.authentication.services.JwtService;
import com.ubereats.ubereatsclone.customer.dto.CustomerDto;
import com.ubereats.ubereatsclone.customer.services.CustomerAuthService;
import com.ubereats.ubereatsclone.customer.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Add new customer.", description = "Simply pass a CustomerDTO as Json to add a new customer.")
    public ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerDto customerDto) {
        log.info("Creating new customer.");
        CustomerDto addedCustomerDto = this.customerAuthService.registerCustomer(customerDto);

        return new ResponseEntity<>(addedCustomerDto, HttpStatus.CREATED);
    }


    @GetMapping("/")
    @Operation(summary = "Get all the customers.", description = "This API is not to be exposed to UI for any access.")
    public List<CustomerDto> getAllCustomers() {
        log.info("Customer list requested.");
        return this.customerService.getAllCustomers();
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @Operation(summary = "Update customer details.", description = "JWT Token is required " +
            "in the header which can be fetched by logging in." +
            "Verifies if the logged in customer is the one who is trying to update details for safety.")
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerDto updatedDetails) {
        log.info("Customer with customer email {} was attempted to be updated.", updatedDetails.getUsername());
        String loggedInEmail = fetchEmailFromHeader();
        if(updatedDetails.getUsername().equals(loggedInEmail)){
            CustomerDto updatedCustomer = this.customerService.updateCustomer(updatedDetails);
            return new ResponseEntity<>(updatedCustomer, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @Operation(summary = "Delete customer using email.", description = "The customer has to be logged in / JWT Token has to be valid.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Deleted."),
            @ApiResponse(responseCode = "400", description = "Verify email id and try again.")
    })
    public ResponseEntity<?> deleteByEmail(@RequestBody String emailId) {
        log.info("Attempting to delete customer with email-id = {}", emailId);
        String loggedInEmail = fetchEmailFromHeader();
        if(loggedInEmail.equals(emailId.trim())) {
            String email = loggedInEmail;
            customerService.deleteCustomerByEmail(email);
        } else {
            return ResponseEntity.badRequest().body("Please verify email.");
        }
        return ResponseEntity.ok(Map.of("message", "Customer deleted by email."));
    }

    @DeleteMapping("/deleteAll")
    @Operation(summary = "Delete all the customers.", description = "Only for testing. This API is not to be exposed to UI for any access.")
    public ResponseEntity<?> deleteAll() {
        log.info("Customers erased.");
        this.customerService.deleteAll();
        return ResponseEntity.ok(Map.of("message", "Customers deleted."));
    }

    @GetMapping("/information")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @Operation(summary = "Find customer by email-id.")
    public CustomerDto findByEmailID() {
        String emailId = fetchEmailFromHeader();
        log.info("Customer with email id {} was searched for.", emailId);
        return this.customerService.getCustomerByEmailId(emailId);
    }

    @PutMapping("/addFood/{foodId}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @Operation(summary = "Add food to cart.", description = "Adds food with foodId to the cart of the customer currently logged in." +
            "Logged in customer - Non-expired JWT Token with header.")
    public Boolean addFoodToCustomerCart(@PathVariable Long foodId) {
        Long customerId = fetchIdFromHeader();
        log.info("Customer {} added food {} to the cart.", customerId, foodId);
        return this.customerService.addFoodToCustomerCart(customerId, foodId);
    }

    @PutMapping("/deleteFood/{foodId}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @Operation(summary = "Delete food from cart.", description = "Delete the foodId from the cart of the customer logged in.")
    public Boolean deleteFoodFromCustomerCart(@PathVariable Long foodId) {
        Long customerId = fetchIdFromHeader();
        log.info("Food {} was deleted from the cart of customer {}", foodId, customerId);
        return this.customerService.removeFoodFromCustomerCart(customerId, foodId);
    }

    @GetMapping("/cartTotal")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @Operation(summary = "Get customer cart total.", description = "Returns the total cart value of the logged in customer's cart.")
    public double getCartTotal() {
        Long customerId = fetchIdFromHeader();
        log.info("Customer {} cart total requested.", customerId);
        return this.customerService.calculateTotalValueOfCart(customerId);
    }

    @PutMapping("/addToFav/{restaurantId}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @Operation(summary = "Add to favourites.", description = "Adds the restaurantId to the list of favourite restaurants.")
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

    @PostMapping("/uberOne")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @Operation(summary = "Register uberOne", description = "Registers the customer to uberOne for the perks of this membership.")
    public CustomerDto startUberOne() {
        Long customerId = fetchIdFromHeader();
        log.info("Customer {} being registered for UberOne membership.", customerId);
        CustomerDto dto = customerService.uberOneMemberStart(customerId);
        return dto;
    }

    @PostMapping("/authenticate")
    @Operation(summary = "LogIn as customer.", description = "Validates the customer information. " +
            "Returns a JWT token which can be passed with other requests which need authentication and authorization.")
    public ResponseEntity<?> customerLogin(@RequestBody LoginCredentials credentials) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if(authentication.isAuthenticated()) {
//            return jwtService.generateToken(credentials.getUsername());
            return ResponseEntity.ok(jwtService.generateToken(credentials.getUsername()));
        } else {
            throw new UsernameNotFoundException("Username not found.");
        }
    }

    private Long fetchIdFromHeader() {
        return customerService.getCustomerByEmailId(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
    }

    private String fetchEmailFromHeader() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
