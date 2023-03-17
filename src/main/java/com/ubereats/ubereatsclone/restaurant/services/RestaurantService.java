package com.ubereats.ubereatsclone.restaurant.services;

import com.ubereats.ubereatsclone.restaurant.dto.RestaurantDto;
import com.ubereats.ubereatsclone.food.entity.FoodItem;
import org.springframework.security.core.context.SecurityContext;

import java.util.List;

public interface RestaurantService {

    RestaurantDto addNewRestaurant(RestaurantDto restaurantDto);

    void removeRestaurantById(Long restaurantId);

    void toggleRestaurantOperationStatus(Long restaurantId);

    List<RestaurantDto> getAllRestaurants();

    RestaurantDto getRestaurantById(Long id);

    RestaurantDto updateRestaurant(RestaurantDto updatedDetails, Long id);

    // FOOD APIs
    FoodItem addFoodItemToRestaurant(FoodItem foodItem, Long restaurantId);

    List<FoodItem> getFoodItemsByRestaurantId(Long restaurantId);

    FoodItem updateFoodItem(FoodItem food, Long foodId);

    void deleteFoodItem(Long foodId);

    List<RestaurantDto> getRestaurantsByCuisine(String cuisine);

    List<RestaurantDto> getRestaurantsByName(String name);

    SecurityContext restaurantLogin(String email, String password);


}
