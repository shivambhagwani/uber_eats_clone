package com.ubereats.ubereatsclone.restaurant.services;

import com.ubereats.ubereatsclone.restaurant.dto.RestaurantEmployeeDto;
import com.ubereats.ubereatsclone.restaurant.entity.RestaurantEmployee;

import java.util.List;

public interface RestaurantEmployeeService {

    RestaurantEmployeeDto addChef(RestaurantEmployeeDto restaurantEmployeeDto, Long restaurantId);

    RestaurantEmployeeDto addAdmin(RestaurantEmployeeDto restaurantEmployeeDto, Long restaurantId);

    List<RestaurantEmployeeDto> getAllEmployees(Long restaurantId);

    RestaurantEmployee getEmployeeById(Long empId);

    RestaurantEmployee getEmployeeByEmail(String employeeEmail);


}
