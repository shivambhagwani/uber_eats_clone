package com.ubereats.ubereatsclone.controllers;

import com.ubereats.ubereatsclone.dtos.CustomerDto;
import com.ubereats.ubereatsclone.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
@Slf4j
public class CustomerController {


    @Autowired
    CustomerService customerService;

    //Add new customer.
    @PostMapping("/")
    public ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerDto customerDto) {
        log.info("Creating new customer.");
        CustomerDto addedCustomerDto = this.customerService.createNewCustomer(customerDto);

        return new ResponseEntity<CustomerDto>(addedCustomerDto, HttpStatus.CREATED);
    }


    @GetMapping("/{customerId}")
    public CustomerDto getCustomerById(@PathVariable Long customerId) {
        log.info("Getting customer with id {}.", customerId);
        try {
            CustomerDto customerDto = this.customerService.getCustomerById(customerId);
            return customerDto;
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

    @DeleteMapping("/{customerId}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable Long customerId) {
        log.info("Customer with customer id {} was deleted.", customerId);
        this.customerService.deleteCustomerById(customerId);
        return ResponseEntity.ok(Map.of("message", "Customer deleted."));
    }

    @DeleteMapping("/delete")
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

    @PutMapping("/{customerId}/food/{foodId}")
    public Boolean addFoodToCustomerCart(@PathVariable Long customerId, @PathVariable Long foodId) {
        log.info("Customer {} added food {} to the cart.", customerId, foodId);
        return this.customerService.addFoodToCustomerCart(customerId, foodId);
    }

    @GetMapping("/cartTotal/{customerId}")
    public double getCartTotal(@PathVariable Long customerId) {
        log.info("Customer {} cart total requested.", customerId);
        return this.customerService.calculateTotalValueOfCart(customerId);
    }

    @PutMapping("/{customerId}/deleteFood/{foodId}")
    public Boolean deleteFoodFromCustomerCart(@PathVariable Long customerId, @PathVariable Long foodId) {
        log.info("Food {} was deleted from the cart of customer {}", foodId, customerId);
        return this.customerService.removeFoodFromCustomerCart(customerId, foodId);
    }
}