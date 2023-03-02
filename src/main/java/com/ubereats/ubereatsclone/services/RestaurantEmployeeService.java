package com.ubereats.ubereatsclone.services;

import com.ubereats.ubereatsclone.dtos.RestaurantEmployeeDto;

public interface RestaurantEmployeeService {

    RestaurantEmployeeDto addChef(RestaurantEmployeeDto restaurantEmployeeDto, Long restaurantId);

    RestaurantEmployeeDto addAdmin(RestaurantEmployeeDto restaurantEmployeeDto, Long restaurantId);


}
