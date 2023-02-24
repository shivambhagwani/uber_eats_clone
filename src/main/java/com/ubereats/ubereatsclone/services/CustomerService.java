package com.ubereats.ubereatsclone.services;

import com.ubereats.ubereatsclone.dtos.CustomerDto;
import com.ubereats.ubereatsclone.entities.Customer;

import java.util.List;

public interface CustomerService {

    //Adding new customer to database
    public CustomerDto createNewCustomer(CustomerDto customerDto);

    //Getting single customer detail from database by ID.
    public CustomerDto getCustomerById(Long customerId);

    //Getting all customers
    public List<CustomerDto> getAllCustomers();

    //Update customer detail
    public CustomerDto updateCustomer(CustomerDto updatedDetails, Long customerId);

    //delete customer using Id.
    public void deleteCustomerById(Long customerId);

    //delete all
    public void deleteAll();

    //find using email ID.
    public CustomerDto getCustomerByEmailId(String emailId);

}
