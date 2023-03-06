package com.ubereats.ubereatsclone.controllers;

import com.ubereats.ubereatsclone.dtos.RestaurantEmployeeDto;
import com.ubereats.ubereatsclone.entities.RestaurantEmployee;
import com.ubereats.ubereatsclone.services.RestaurantEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class RestaurantEmployeeController {
    @Autowired
    RestaurantEmployeeService restaurantEmployeeService;
    @PostMapping("/addChef/{restaurantId}")
    public ResponseEntity<RestaurantEmployeeDto> addChefToRestaurant(@RequestBody RestaurantEmployeeDto chefDto, @PathVariable Long restaurantId) {
        RestaurantEmployeeDto addedChef = restaurantEmployeeService.addChef(chefDto, restaurantId);

        return new ResponseEntity(addedChef, HttpStatus.CREATED);
    }

    @PostMapping("/addAdmin/{restaurantId}")
    public ResponseEntity<RestaurantEmployeeDto> addAdminToRestaurant(@RequestBody RestaurantEmployeeDto adminDto, @PathVariable Long restaurantId) {
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
