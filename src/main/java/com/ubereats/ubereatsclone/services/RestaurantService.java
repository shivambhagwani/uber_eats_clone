package com.ubereats.ubereatsclone.services;

import com.ubereats.ubereatsclone.dtos.RestaurantDto;

import java.util.List;

public interface RestaurantService {

    RestaurantDto addNewRestaurant(RestaurantDto restaurantDto);

    void removeRestaurantById(Long restaurantId);

    void toggleRestaurantOperationStatus(Long restaurantId);

//    String getMenuIdOfRestaurantById(Long restaurantId); //will later help us to modify menu items

    List<RestaurantDto> getAllRestaurants();

    RestaurantDto getRestaurantById(Long id);

    RestaurantDto updateRestaurant(RestaurantDto updatedDetails, Long id);

}
