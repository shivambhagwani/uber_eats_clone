package com.ubereats.ubereatsclone.restaurant.services.impl;

import com.ubereats.ubereatsclone.restaurant.dto.RestaurantEmployeeDto;
import com.ubereats.ubereatsclone.restaurant.entity.Restaurant;
import com.ubereats.ubereatsclone.restaurant.entity.RestaurantEmployee;
import com.ubereats.ubereatsclone.restaurant.entity.RestaurantEmployeeEnum;
import com.ubereats.ubereatsclone.util.exceptions.DetailNotFoundException;
import com.ubereats.ubereatsclone.restaurant.repository.RestaurantEmployeeRepository;
import com.ubereats.ubereatsclone.restaurant.repository.RestaurantRepository;
import com.ubereats.ubereatsclone.restaurant.services.RestaurantEmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public RestaurantEmployeeDto addChef(RestaurantEmployeeDto restaurantEmployeeDto, Long restaurantId) {
        RestaurantEmployee chef = new RestaurantEmployee();
        chef.setName(restaurantEmployeeDto.getName());
        chef.setAge(restaurantEmployeeDto.getAge());
        chef.setEmail(restaurantEmployeeDto.getEmail());
        chef.setPhone(restaurantEmployeeDto.getPhone());
        Restaurant chefRes = restaurantRepository.findById(restaurantId).orElseThrow();
        chef.setRestaurant(chefRes);
        chef.setJobRole(RestaurantEmployeeEnum.CHEF);

        RestaurantEmployee addedChef = restaurantEmployeeRepository.save(chef);

        return modelMapper.map(addedChef, RestaurantEmployeeDto.class);
    }

    @Override
    public RestaurantEmployeeDto addAdmin(RestaurantEmployeeDto restaurantEmployeeDto, Long restaurantId) {
        RestaurantEmployee admin = new RestaurantEmployee();
        admin.setName(restaurantEmployeeDto.getName());
        admin.setAge(restaurantEmployeeDto.getAge());
        admin.setPhone(restaurantEmployeeDto.getPhone());
        admin.setEmail(restaurantEmployeeDto.getEmail());
        try {
            Restaurant adminRes = restaurantRepository.findById(restaurantId).orElseThrow();
            admin.setRestaurant(adminRes);
        } catch(Exception e) {
            return null;
        }

        admin.setJobRole(RestaurantEmployeeEnum.ADMIN);

        RestaurantEmployee addedAdmin = restaurantEmployeeRepository.save(admin);

        return modelMapper.map(addedAdmin, RestaurantEmployeeDto.class);
    }

    @Override
    public List<RestaurantEmployeeDto> getAllEmployees(Long restaurantId) {
        List<RestaurantEmployee> employees = restaurantEmployeeRepository.findAll();

        List<RestaurantEmployeeDto> emps = new ArrayList<>();
        for(RestaurantEmployee e : employees) {
            if(e.getRestaurant().getRestaurantId() == restaurantId) {
                RestaurantEmployeeDto temp = new RestaurantEmployeeDto();
                temp.setName(e.getName());
                temp.setAge(e.getAge());
                temp.setPhone(e.getPhone());
                temp.setEmail(e.getEmail());
                temp.setRestaurant(e.getRestaurant());
                temp.setJobRole(e.getJobRole());
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
        return restaurantEmployeeRepository.findByEmail(employeeEmail);
    }
}
