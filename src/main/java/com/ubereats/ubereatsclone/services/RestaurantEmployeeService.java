package com.ubereats.ubereatsclone.services;

import com.ubereats.ubereatsclone.dtos.RestaurantEmployeeDto;
import com.ubereats.ubereatsclone.entities.RestaurantEmployee;

import java.util.List;

public interface RestaurantEmployeeService {

    RestaurantEmployeeDto addChef(RestaurantEmployeeDto restaurantEmployeeDto, Long restaurantId);

    RestaurantEmployeeDto addAdmin(RestaurantEmployeeDto restaurantEmployeeDto, Long restaurantId);

    List<RestaurantEmployeeDto> getAllEmployees(Long restaurantId);

    RestaurantEmployee getEmployeeById(Long empId);

    RestaurantEmployee getEmployeeByEmail(String employeeEmail);


}
