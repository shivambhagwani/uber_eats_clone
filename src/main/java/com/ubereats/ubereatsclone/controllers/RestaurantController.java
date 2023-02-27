package com.ubereats.ubereatsclone.controllers;

import com.ubereats.ubereatsclone.dtos.RestaurantDto;
import com.ubereats.ubereatsclone.entities.FoodItem;
import com.ubereats.ubereatsclone.entities.Restaurant;
import com.ubereats.ubereatsclone.repositories.FoodItemRepository;
import com.ubereats.ubereatsclone.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    private FoodItemRepository foodItemRepository;

    @PostMapping("/")
    public ResponseEntity<RestaurantDto> addNewRestaurant(@RequestBody RestaurantDto restaurantDto) {

        RestaurantDto addedRestaurant = this.restaurantService.addNewRestaurant(restaurantDto);

        return new ResponseEntity<>(addedRestaurant, HttpStatus.CREATED);
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<?> removeRestaurantById(@PathVariable Long restaurantId) {

        this.restaurantService.removeRestaurantById(restaurantId);

        return ResponseEntity.ok(Map.of("message", "Restaurant Deleted"));
    }

    @PutMapping("/toggleStatus/{restaurantId}")
    public ResponseEntity<?> toggleOperationStatus(@PathVariable Long restaurantId){

        this.restaurantService.toggleRestaurantOperationStatus(restaurantId);

        return ResponseEntity.ok(Map.of("message", "Restaurant Operation Status Toggled."));
    }

//    @GetMapping("/menuId/{restaurantId}")
//    public String getMenuIdOfRestaurantById(@PathVariable Long restaurantId) {
//        return this.restaurantService.getMenuIdOfRestaurantById(restaurantId);
//    }

    @GetMapping("/")
    public List<RestaurantDto> getAllRestaurants() {
        return this.restaurantService.getAllRestaurants();
    }

    @GetMapping("/{restaurantId}")
    public RestaurantDto getRestaurantById(@PathVariable Long restaurantId) {
        return this.restaurantService.getRestaurantById(restaurantId);
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDto> updateRestaurantDetails(@RequestBody RestaurantDto updatedDetails, @PathVariable Long restaurantId) {
        RestaurantDto updates = this.restaurantService.updateRestaurant(updatedDetails, restaurantId);

        return new ResponseEntity<>(updates, HttpStatus.ACCEPTED);
    }

    //Food Item APIs
    @PostMapping("/{restaurantId}/food")
    public FoodItem addFoodItemToRestaurant(@RequestBody FoodItem food, @PathVariable Long restaurantId) {
        FoodItem food1 = restaurantService.addFoodItemToRestaurant(food, restaurantId);

        return food1;
    }

    @GetMapping("/{restaurantId}/food")
    public List<FoodItem> getFoodItemsOfRestaurantById(@PathVariable Long restaurantId) {

        List<FoodItem> restaurantFood = this.restaurantService.getFoodItemsByRestaurantId(restaurantId);

        return  restaurantFood;
    }

    @PutMapping("/food/{foodId}")
    public ResponseEntity<FoodItem> updateFoodItem(@RequestBody FoodItem food, @PathVariable Long foodId) {
        FoodItem food1 = restaurantService.updateFoodItem(food, foodId);

        return new ResponseEntity<>(food1, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/food/{foodId}")
    public ResponseEntity<?> removeFoodItemById(@PathVariable Long foodId) {

        this.restaurantService.deleteFoodItem(foodId);

        return ResponseEntity.ok(Map.of("message", "Food Item Deleted"));
    }

}
