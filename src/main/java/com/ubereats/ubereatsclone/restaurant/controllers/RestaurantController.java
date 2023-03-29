package com.ubereats.ubereatsclone.restaurant.controllers;

import com.ubereats.ubereatsclone.restaurant.dto.RestaurantDto;
import com.ubereats.ubereatsclone.food.entity.FoodItem;
import com.ubereats.ubereatsclone.restaurant.entity.Restaurant;
import com.ubereats.ubereatsclone.restaurant.repository.RestaurantRepository;
import com.ubereats.ubereatsclone.restaurant.services.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/restaurants")
@Slf4j
public class RestaurantController {
    @Autowired
    RestaurantService restaurantService;

    @PostMapping("/")
    public RestaurantDto addNewRestaurant(@RequestBody RestaurantDto restaurantDto) {
        log.info("New restaurant added to list.");

        RestaurantDto addedRestaurant = this.restaurantService.addNewRestaurant(restaurantDto);

        return addedRestaurant;
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<?> removeRestaurantById(@PathVariable Long restaurantId) {
        log.info("Restaurant with id {} was deleted.", restaurantId);

        this.restaurantService.removeRestaurantById(restaurantId);

        return ResponseEntity.ok(Map.of("message", "Restaurant Deleted"));
    }

    @PutMapping("/toggleStatus/{restaurantId}")
    public ResponseEntity<?> toggleOperationStatus(@PathVariable Long restaurantId){
        log.info("Restaurant {} changed operation status.", restaurantId);

        this.restaurantService.toggleRestaurantOperationStatus(restaurantId);

        return ResponseEntity.ok(Map.of("message", "Restaurant Operation Status Toggled."));
    }


    @GetMapping("/")
    public List<RestaurantDto> getAllRestaurants() {
        return this.restaurantService.getAllRestaurants();
    }

    @GetMapping("/{restaurantId}")
    public RestaurantDto getRestaurantById(@PathVariable Long restaurantId) {
        return this.restaurantService.getRestaurantById(restaurantId);
    }

    @GetMapping("/cuisine")
    public List<RestaurantDto> getRestaurantsByCuisine(@RequestBody String cuisine) {
        return restaurantService.getRestaurantsByCuisine(cuisine);
    }

    @GetMapping("/restaurantName")
    public List<RestaurantDto> getRestaurantsByName(@RequestBody String name) {
        return restaurantService.getRestaurantsByName(name);
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDto> updateRestaurantDetails(@RequestBody RestaurantDto updatedDetails, @PathVariable Long restaurantId) {
        RestaurantDto updates = this.restaurantService.updateRestaurant(updatedDetails, restaurantId);

        return new ResponseEntity<>(updates, HttpStatus.ACCEPTED);
    }

    //Food Item APIs
    @PostMapping("/{restaurantId}/food")
    public FoodItem addFoodItemToRestaurant(@RequestBody FoodItem food, @PathVariable Long restaurantId) {
        log.info("Restaurant {} added a new food item.", restaurantId);
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
        log.info("Restaurant updated details of food {}.", foodId);
        FoodItem food1 = restaurantService.updateFoodItem(food, foodId);

        return new ResponseEntity<>(food1, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/food/{foodId}")
    public ResponseEntity<?> removeFoodItemById(@PathVariable Long foodId) {
        log.info("Food {} was deleted from menu.", foodId);

        this.restaurantService.deleteFoodItem(foodId);

        return ResponseEntity.ok(Map.of("message", "Food Item Deleted"));
    }

    @GetMapping("/category")
    public List<RestaurantDto> getRestaurantByCategory(@RequestBody String category) {
        List<RestaurantDto> res = restaurantService.getRestaurantsByCategory(category);
        return res;
    }

    @GetMapping("/popular")
    public List<Long> getPopularRestaurants() {
        return restaurantService.getPopularRestaurants();
    }
}
