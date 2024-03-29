package com.ubereats.ubereatsclone.restaurant.services;

import com.ubereats.ubereatsclone.restaurant.dto.RestaurantDto;
import com.ubereats.ubereatsclone.food.entity.FoodItem;
import org.springframework.security.core.context.SecurityContext;

import java.util.List;

public interface RestaurantService {

    RestaurantDto addNewRestaurant(RestaurantDto restaurantDto);

    Boolean restaurantExists(Long restaurantId);

    void removeRestaurantById(Long restaurantId);

    void toggleRestaurantOperationStatus(Long restaurantId);

    List<RestaurantDto> getAllRestaurants();

    RestaurantDto getRestaurantById(Long id);

    List<RestaurantDto> getRestaurantsByCategory(String category);

    RestaurantDto updateRestaurant(RestaurantDto updatedDetails, Long id);

    // FOOD APIs
    FoodItem addFoodItemToRestaurant(FoodItem foodItem, Long restaurantId);

    List<FoodItem> getFoodItemsByRestaurantId(Long restaurantId);

    FoodItem updateFoodItem(FoodItem food, Long foodId);

    void deleteFoodItem(Long foodId);

    List<RestaurantDto> getRestaurantsByCuisine(String cuisine);

    List<RestaurantDto> getRestaurantsByName(String name);

    List<Long> getPopularRestaurants();

}
