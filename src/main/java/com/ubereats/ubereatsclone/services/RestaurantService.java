package com.ubereats.ubereatsclone.services;

import com.ubereats.ubereatsclone.dtos.RestaurantDto;
import com.ubereats.ubereatsclone.entities.FoodItem;

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

}
