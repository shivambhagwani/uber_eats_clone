package com.ubereats.ubereatsclone.restaurant.services.impl;

import com.ubereats.ubereatsclone.restaurant.dto.RestaurantEmployeeDto;
import com.ubereats.ubereatsclone.restaurant.entity.Restaurant;
import com.ubereats.ubereatsclone.restaurant.entity.RestaurantEmployee;
import com.ubereats.ubereatsclone.util.exceptions.DetailNotFoundException;
import com.ubereats.ubereatsclone.restaurant.repository.RestaurantEmployeeRepository;
import com.ubereats.ubereatsclone.restaurant.repository.RestaurantRepository;
import com.ubereats.ubereatsclone.restaurant.services.RestaurantEmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantEmployeeServiceImpl implements RestaurantEmployeeService {

    @Autowired
    RestaurantEmployeeRepository restaurantEmployeeRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    //cache put
    @Override
    public RestaurantEmployeeDto addChef(RestaurantEmployeeDto restaurantEmployeeDto, Long restaurantId) {
        RestaurantEmployee employee = new RestaurantEmployee();
        employee.setFullName(restaurantEmployeeDto.getFullName());
        employee.setAge(restaurantEmployeeDto.getAge());
        employee.setContactNumber(restaurantEmployeeDto.getPhone());
        employee.setUsername(restaurantEmployeeDto.getUsername());
        employee.setPassword(passwordEncoder.encode(restaurantEmployeeDto.getPassword()));
        try {
            Restaurant adminRes = restaurantRepository.findById(restaurantId).orElseThrow();
            employee.setRestaurant(adminRes);
        } catch(Exception e) {
            return null;
        }

        employee.setAuthorities("CHEF");

        RestaurantEmployee addedAdmin = restaurantEmployeeRepository.save(employee);

        return modelMapper.map(addedAdmin, RestaurantEmployeeDto.class);
    }

    //cache put
    @Override
    public RestaurantEmployeeDto addAdmin(RestaurantEmployeeDto restaurantEmployeeDto, Long restaurantId) {
        RestaurantEmployee employee = new RestaurantEmployee();
        employee.setFullName(restaurantEmployeeDto.getFullName());
        employee.setAge(restaurantEmployeeDto.getAge());
        employee.setContactNumber(restaurantEmployeeDto.getPhone());
        employee.setUsername(restaurantEmployeeDto.getUsername());
        employee.setPassword(passwordEncoder.encode(restaurantEmployeeDto.getPassword()));
        try {
            Restaurant adminRes = restaurantRepository.findById(restaurantId).orElseThrow();
            employee.setRestaurant(adminRes);
        } catch(Exception e) {
            return null;
        }

        employee.setAuthorities("ADMIN");

        RestaurantEmployee addedAdmin = restaurantEmployeeRepository.save(employee);

        return modelMapper.map(addedAdmin, RestaurantEmployeeDto.class);
    }

    //cache
    @Override
    public List<RestaurantEmployeeDto> getAllEmployees(Long restaurantId) {
        List<RestaurantEmployee> employees = restaurantEmployeeRepository.findAll();

        List<RestaurantEmployeeDto> emps = new ArrayList<>();
        for(RestaurantEmployee e : employees) {
            if(e.getRestaurant().getRestaurantId() == restaurantId) {
                RestaurantEmployeeDto temp = new RestaurantEmployeeDto();
                temp.setFullName(e.getFullName());
                temp.setAge(e.getAge());
                temp.setPhone(e.getContactNumber());
                temp.setRestaurant(e.getRestaurant());
                temp.setAuthorities(e.getAuthorities());
                emps.add(temp);
            }
        }

        return emps;
    }

    @Override
    public RestaurantEmployee getEmployeeById(Long empId) {
        return restaurantEmployeeRepository.findById(empId).orElseThrow(() -> new DetailNotFoundException("RestaurantEmployee", "empOId", empId));
    }

    @Override
    public RestaurantEmployee getEmployeeByEmail(String employeeEmail) {
        return restaurantEmployeeRepository.findByUsername(employeeEmail);
    }
}
