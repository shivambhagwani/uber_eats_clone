package com.ubereats.ubereatsclone.services.impl;

import com.ubereats.ubereatsclone.dtos.CustomerDto;
import com.ubereats.ubereatsclone.entities.Customer;
import com.ubereats.ubereatsclone.entities.CustomerCart;
import com.ubereats.ubereatsclone.repositories.CustomerRepository;
import com.ubereats.ubereatsclone.services.CustomerAddressService;
import com.ubereats.ubereatsclone.services.CustomerCartService;
import com.ubereats.ubereatsclone.services.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ubereats.ubereatsclone.exceptions.DetailNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerCartService customerCartService;

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
}
