package com.ubereats.ubereatsclone.customer.services.impl;

import com.ubereats.ubereatsclone.customer.dto.CustomerDto;
import com.ubereats.ubereatsclone.customer.entity.Customer;
import com.ubereats.ubereatsclone.customer.repository.CustomerRepository;
import com.ubereats.ubereatsclone.customer.services.CustomerAddressService;
import com.ubereats.ubereatsclone.customer.services.CustomerAuthService;
import com.ubereats.ubereatsclone.customer.services.CustomerCartService;
import com.ubereats.ubereatsclone.util.exceptions.UserAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomerAuthServiceImpl implements CustomerAuthService {

    final
    CustomerRepository customerRepository;
    final
    ModelMapper modelMapper;
    final
    PasswordEncoder passwordEncoder;

    final
    CustomerCartService customerCartService;
    final
    CustomerAddressService customerAddressService;

    public CustomerAuthServiceImpl(CustomerAddressService customerAddressService, CustomerCartService customerCartService, CustomerRepository customerRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.customerAddressService = customerAddressService;
        this.customerCartService = customerCartService;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public CustomerDto registerCustomer(CustomerDto customerDto) {

        if(customerRepository.findByEmail(customerDto.getEmail()) != null) {
            throw new UserAlreadyExistsException("User with email :" + customerDto.getEmail() +" already exists.");
        }

        Customer mappedCustomer = this.modelMapper.map(customerDto, Customer.class);
        mappedCustomer.setRoles("ROLE_CUSTOMER");
        mappedCustomer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
        customerCartService.createNewCart(mappedCustomer.getCustomerCart());
        customerAddressService.createNewAddress(mappedCustomer.getCustomerAddress());
        Customer customerAdded = customerRepository.save(mappedCustomer);

        return this.modelMapper.map(customerAdded, CustomerDto.class);
    }
}
