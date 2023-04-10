package com.ubereats.ubereatsclone.restaurant.controllers;

import com.ubereats.ubereatsclone.restaurant.dto.RestaurantDto;
import com.ubereats.ubereatsclone.food.entity.FoodItem;
import com.ubereats.ubereatsclone.restaurant.services.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/restaurants")
@Slf4j
public class RestaurantController {
    @Autowired
    RestaurantService restaurantService;

    @PostMapping("/")
    @Operation(summary = "Add new restaurant.", description = "Add a new restaurant by providing restaurant information " +
            "as RestaurantDto JSON.")
    public RestaurantDto addNewRestaurant(@RequestBody RestaurantDto restaurantDto) {
        log.info("New restaurant added to list.");

        RestaurantDto addedRestaurant = this.restaurantService.addNewRestaurant(restaurantDto);

        return addedRestaurant;
    }

    @DeleteMapping("/{restaurantId}")
    @Operation(summary = "Remove restaurant by id.", description = "Restaurant deletion from the database using the restaurant ID.")
    public ResponseEntity<?> removeRestaurantById(@PathVariable Long restaurantId) {
        log.info("Restaurant with id {} was deleted.", restaurantId);

        this.restaurantService.removeRestaurantById(restaurantId);

        return ResponseEntity.ok(Map.of("message", "Restaurant Deleted"));
    }

    @PutMapping("/toggleStatus/{restaurantId}")
    @Operation(summary = "Toggle serving status of restaurant.", description = "The restaurants can be operating or not operating " +
            "at any moment of the day. Toggle the status using this API.")
    public ResponseEntity<?> toggleOperationStatus(@PathVariable Long restaurantId){
        log.info("Restaurant {} changed operation status.", restaurantId);

        this.restaurantService.toggleRestaurantOperationStatus(restaurantId);

        return ResponseEntity.ok(Map.of("message", "Restaurant Operation Status Toggled."));
    }


    @GetMapping("/")
    @Operation(summary = "Get all restaurants.", description = "Returns the list of all the restaurants which are in " +
            "the database.")
    public List<RestaurantDto> getAllRestaurants() {
        return this.restaurantService.getAllRestaurants();
    }

    @GetMapping("/{restaurantId}")
    @Operation(summary = "Get a single restaurant.", description = "Get a particular restaurant details using it's id.")
    public RestaurantDto getRestaurantById(@PathVariable Long restaurantId) {
        return this.restaurantService.getRestaurantById(restaurantId);
    }

    @GetMapping("/cuisine")
    @Operation(summary = "Get by cuisine.", description = "Get restaurants which serve a particular cuisine.")
    public List<RestaurantDto> getRestaurantsByCuisine(@RequestBody String cuisine) {
        return restaurantService.getRestaurantsByCuisine(cuisine);
    }

    @GetMapping("/restaurantName")
    @Operation(summary = "Get by name.", description = "Look for the restaurant based on the name.")
    public List<RestaurantDto> getRestaurantsByName(@RequestBody String name) {
        return restaurantService.getRestaurantsByName(name);
    }

    @PutMapping("/{restaurantId}")
    @Operation(summary = "Update restaurant details.", description = "Pass updated details as parameters.")
    public ResponseEntity<RestaurantDto> updateRestaurantDetails(@RequestBody RestaurantDto updatedDetails, @PathVariable Long restaurantId) {
        RestaurantDto updates = this.restaurantService.updateRestaurant(updatedDetails, restaurantId);

        return new ResponseEntity<>(updates, HttpStatus.ACCEPTED);
    }

    //Food Item APIs
    @PostMapping("/{restaurantId}/food")
    @Operation(summary = "Add food item to the restaurant menu.", description = "Food item to be added can be passed as " +
            "JSON along with the restaurant ID in the URL parameter.")
    public FoodItem addFoodItemToRestaurant(@RequestBody FoodItem food, @PathVariable Long restaurantId) {
        log.info("Restaurant {} added a new food item.", restaurantId);
        FoodItem food1 = restaurantService.addFoodItemToRestaurant(food, restaurantId);

        return food1;
    }

    @GetMapping("/{restaurantId}/food")
    @Operation(summary = "Add new restaurant.", description = "Add a new restaurant by providing restaurant information " +
            "as RestaurantDto JSON.")
    public List<FoodItem> getFoodItemsOfRestaurantById(@PathVariable Long restaurantId) {

        List<FoodItem> restaurantFood = this.restaurantService.getFoodItemsByRestaurantId(restaurantId);

        return  restaurantFood;
    }

    @PutMapping("/food/{foodId}")
    @Operation(summary = "Update food item in the menu.")
    public ResponseEntity<FoodItem> updateFoodItem(@RequestBody FoodItem food, @PathVariable Long foodId) {
        log.info("Restaurant updated details of food {}.", foodId);
        FoodItem food1 = restaurantService.updateFoodItem(food, foodId);

        return new ResponseEntity<>(food1, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/food/{foodId}")
    @Operation(summary = "Delete food item.")
    public ResponseEntity<?> removeFoodItemById(@PathVariable Long foodId) {
        log.info("Food {} was deleted from menu.", foodId);

        this.restaurantService.deleteFoodItem(foodId);

        return ResponseEntity.ok(Map.of("message", "Food Item Deleted"));
    }

    @GetMapping("/category")
    @Operation(summary = "Get restaurant by category.")
    public List<RestaurantDto> getRestaurantByCategory(@RequestBody String category) {
        List<RestaurantDto> res = restaurantService.getRestaurantsByCategory(category);
        return res;
    }

    @GetMapping("/popular")
    @Operation(summary = "Get past 24 hrs popular restaurants.")
    public List<Long> getPopularRestaurants() {
        return restaurantService.getPopularRestaurants();
    }
}
