package com.ubereats.ubereatsclone.restaurant.controllers;

import com.ubereats.ubereatsclone.restaurant.dto.RestaurantEmployeeDto;
import com.ubereats.ubereatsclone.restaurant.services.RestaurantEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@Slf4j
public class RestaurantEmployeeController {
    @Autowired
    RestaurantEmployeeService restaurantEmployeeService;
    @PostMapping("/addChef/{restaurantId}")
    public ResponseEntity<RestaurantEmployeeDto> addChefToRestaurant(@RequestBody RestaurantEmployeeDto chefDto, @PathVariable Long restaurantId) {
        log.info("A new chef has joined restaurant {}.", restaurantId);
        RestaurantEmployeeDto addedChef = restaurantEmployeeService.addChef(chefDto, restaurantId);

        return new ResponseEntity(addedChef, HttpStatus.CREATED);
    }

    @PostMapping("/addAdmin/{restaurantId}")
    public ResponseEntity<RestaurantEmployeeDto> addAdminToRestaurant(@RequestBody RestaurantEmployeeDto adminDto, @PathVariable Long restaurantId) {
        log.info("A new admin has joined restaurant {}.", restaurantId);
        RestaurantEmployeeDto addAdmin = restaurantEmployeeService.addAdmin(adminDto, restaurantId);

        if(addAdmin == null) {
            return  new ResponseEntity(addAdmin, HttpStatus.NOT_ACCEPTABLE);
        } else {
            return new ResponseEntity(addAdmin, HttpStatus.CREATED);
        }
    }

    @GetMapping("/{restaurantId}")
    public List<RestaurantEmployeeDto> getAllEmployeesOfRestaurant(@PathVariable Long restaurantId) {
        List<RestaurantEmployeeDto> employees = restaurantEmployeeService.getAllEmployees(restaurantId);
        return employees;
    }

}
