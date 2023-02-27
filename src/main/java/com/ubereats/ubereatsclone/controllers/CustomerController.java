package com.ubereats.ubereatsclone.controllers;

import com.ubereats.ubereatsclone.dtos.CustomerDto;
import com.ubereats.ubereatsclone.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {


    @Autowired
    CustomerService customerService;

    //Add new customer.
    @PostMapping("/")
    public ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerDto customerDto) {
        CustomerDto addedCustomerDto = this.customerService.createNewCustomer(customerDto);

        return new ResponseEntity<CustomerDto>(addedCustomerDto, HttpStatus.CREATED);
    }


    @GetMapping("/{customerId}")
    public CustomerDto getCustomerById(@PathVariable Long customerId) {

        CustomerDto customerDto = this.customerService.getCustomerById(customerId);
        return customerDto;
    }

    @GetMapping("/")
    public List<CustomerDto> getAllCustomers() {
        return this.customerService.getAllCustomers();
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerDto> updateCustomerById(@RequestBody CustomerDto updatedDetails, @PathVariable Long customerId) {
        CustomerDto updatedCustomer = this.customerService.updateCustomer(updatedDetails, customerId);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable Long customerId) {
        this.customerService.deleteCustomerById(customerId);
        return ResponseEntity.ok(Map.of("message", "Customer deleted."));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAll() {
        this.customerService.deleteAll();
        return ResponseEntity.ok(Map.of("message", "Customers deleted."));
    }

    @GetMapping("email/{emailId}")
    public CustomerDto findByEmailID(@PathVariable String emailId) {
        return this.customerService.getCustomerByEmailId(emailId);

    }
}
