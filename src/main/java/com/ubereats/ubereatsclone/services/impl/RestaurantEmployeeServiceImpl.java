package com.ubereats.ubereatsclone.services.impl;

import com.ubereats.ubereatsclone.dtos.RestaurantDto;
import com.ubereats.ubereatsclone.dtos.RestaurantEmployeeDto;
import com.ubereats.ubereatsclone.entities.Restaurant;
import com.ubereats.ubereatsclone.entities.RestaurantEmployee;
import com.ubereats.ubereatsclone.entities.RestaurantEmployeeEnum;
import com.ubereats.ubereatsclone.repositories.RestaurantEmployeeRepository;
import com.ubereats.ubereatsclone.repositories.RestaurantRepository;
import com.ubereats.ubereatsclone.services.RestaurantEmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantEmployeeServiceImpl implements RestaurantEmployeeService {

    @Autowired
    RestaurantEmployeeRepository restaurantEmployeeRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public RestaurantEmployeeDto addChef(RestaurantEmployeeDto restaurantEmployeeDto, Long restaurantId) {
        RestaurantEmployee chef = new RestaurantEmployee();
        chef.setName(restaurantEmployeeDto.getName());
        chef.setAge(restaurantEmployeeDto.getAge());
        Restaurant chefRes = restaurantRepository.findById(restaurantId).orElseThrow();
        chef.setRestaurant(chefRes);
        chef.setJob_role(RestaurantEmployeeEnum.CHEF);

        RestaurantEmployee addedChef = restaurantEmployeeRepository.save(chef);

        return modelMapper.map(addedChef, RestaurantEmployeeDto.class);
    }

    @Override
    public RestaurantEmployeeDto addAdmin(RestaurantEmployeeDto restaurantEmployeeDto, Long restaurantId) {
        RestaurantEmployee chef = new RestaurantEmployee();
        chef.setName(restaurantEmployeeDto.getName());
        chef.setAge(restaurantEmployeeDto.getAge());
        try {
            Restaurant chefRes = restaurantRepository.findById(restaurantId).orElseThrow();
            chef.setRestaurant(chefRes);
        } catch(Exception e) {
            return null;
        }

        chef.setJob_role(RestaurantEmployeeEnum.ADMIN);

        RestaurantEmployee addedChef = restaurantEmployeeRepository.save(chef);

        return modelMapper.map(addedChef, RestaurantEmployeeDto.class);
    }
}
