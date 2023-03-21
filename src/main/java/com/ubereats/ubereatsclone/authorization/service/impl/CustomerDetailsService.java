package com.ubereats.ubereatsclone.authorization.service.impl;

import com.ubereats.ubereatsclone.authorization.config.CustomerDetails;
import com.ubereats.ubereatsclone.customer.entity.Customer;
import com.ubereats.ubereatsclone.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomerDetailsService implements UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = Optional.ofNullable(customerRepository.findByEmail(username));
        return customer.map(CustomerDetails::new).orElseThrow(() -> new UsernameNotFoundException("The user was not found " + username));
    }
}
