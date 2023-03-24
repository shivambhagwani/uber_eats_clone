package com.ubereats.ubereatsclone.authentication.service.impl;

import com.ubereats.ubereatsclone.authentication.config.CustomUserDetails;
import com.ubereats.ubereatsclone.authentication.dto.UserAuthDetails;
import com.ubereats.ubereatsclone.authentication.dto.repository.UserAuthDetailsRepository;
import com.ubereats.ubereatsclone.customer.entity.Customer;
import com.ubereats.ubereatsclone.customer.repository.CustomerRepository;
import com.ubereats.ubereatsclone.restaurant.entity.RestaurantEmployee;
import com.ubereats.ubereatsclone.restaurant.repository.RestaurantEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RestaurantEmployeeRepository restaurantEmployeeRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = Optional.ofNullable(customerRepository.findByEmail(username));
        Optional<RestaurantEmployee> restaurantEmployee = Optional.ofNullable(restaurantEmployeeRepository.findByEmail(username));
        if(customer != null) {
            return customer.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("The user was not found " + username));
        } else {
            return restaurantEmployee.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("The user was not found " + username));
        }
//        return customer.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("The user was not found " + username));
    }
}
