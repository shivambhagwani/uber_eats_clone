package com.ubereats.ubereatsclone.services.impl;

import com.ubereats.ubereatsclone.dtos.RestaurantDto;
import com.ubereats.ubereatsclone.entities.FoodItem;
import com.ubereats.ubereatsclone.entities.Restaurant;
import com.ubereats.ubereatsclone.exceptions.DetailNotFoundException;
import com.ubereats.ubereatsclone.repositories.FoodItemRepository;
import com.ubereats.ubereatsclone.repositories.RestaurantRepository;
import com.ubereats.ubereatsclone.services.RestaurantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    FoodItemRepository foodItemRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public RestaurantDto addNewRestaurant(RestaurantDto restaurantDto) {
        Restaurant res = this.modelMapper.map(restaurantDto, Restaurant.class);
        Restaurant resAdded = this.restaurantRepository.save(res);

        return this.modelMapper.map(resAdded, RestaurantDto.class);
    }

    @Override
    public void removeRestaurantById(Long restaurantId) {
        this.restaurantRepository.deleteById(restaurantId);
        return;
    }

    @Override
    public void toggleRestaurantOperationStatus(Long restaurantId) {
        Restaurant res = this.restaurantRepository.findById(restaurantId).orElseThrow(() -> new DetailNotFoundException("Restaurant", "restaurantId", restaurantId));

        res.setOperationStatus(!res.getOperationStatus());

        this.restaurantRepository.save(res);

        return;

    }

//    @Override
//    public String getMenuIdOfRestaurantById(Long restaurantId) {
//        Restaurant res = this.restaurantRepository.findById(restaurantId).orElseThrow(() -> new DetailNotFoundException("Restaurant", "restaurantId", restaurantId));
//        return res.getMenuId();
//    }


    @Override
    public List<RestaurantDto> getAllRestaurants() {
        List<Restaurant> restaurants = this.restaurantRepository.findAll();

        List<RestaurantDto> restaurantDtos = restaurants
                                                .stream()
                                                .map(restaurant -> this.modelMapper.map(restaurant, RestaurantDto.class))
                                                .collect(Collectors.toList());

        return restaurantDtos;
    }

    @Override
    public RestaurantDto getRestaurantById(Long id) {
        Restaurant res = this.restaurantRepository.findById(id).orElseThrow(() -> new DetailNotFoundException("Restaurant", "restaurantId", id));
        return this.modelMapper.map(res, RestaurantDto.class);
    }

    @Override
    public RestaurantDto updateRestaurant(RestaurantDto updatedDetails, Long id) {
        Restaurant res = this.restaurantRepository.findById(id).orElseThrow(() -> new DetailNotFoundException("Restaurant", "restaurantId", id));

        res.setRestaurantName(updatedDetails.getRestaurantName());
        res.setRating(updatedDetails.getRating());
        res.setOperationStatus(updatedDetails.isOperationStatus());
        res.setPincode(updatedDetails.getPincode());
        res.setCuisine(updatedDetails.getCuisine());

        Restaurant savedRestaurant = this.restaurantRepository.save(res);

        return this.modelMapper.map(savedRestaurant, RestaurantDto.class);
    }

    @Override
    public FoodItem addFoodItemToRestaurant(FoodItem foodItem, Long restaurantId) {
        foodItem.setRestaurantId(restaurantId);
        FoodItem food = this.foodItemRepository.save(foodItem);

        return food;
    }

    @Override
    public List<FoodItem> getFoodItemsByRestaurantId(Long restaurantId) {
        List<FoodItem> foodItems = foodItemRepository.findAll();
        List<FoodItem> restaurantFood = new ArrayList<>();

        for(FoodItem f : foodItems) {
            if(f.getRestaurantId() == restaurantId)
                restaurantFood.add(f);
        }

        return  restaurantFood;
    }

    @Override
    public FoodItem updateFoodItem(FoodItem food, Long foodId) {
        FoodItem f = this.foodItemRepository.findById(foodId).orElseThrow(() -> new DetailNotFoundException("FoodItem", "foodId", foodId));

        f.setItemName(food.getItemName());
        f.setItemCost(food.getItemCost());

        FoodItem saved = this.foodItemRepository.save(f);
        return saved;
    }

    @Override
    public void deleteFoodItem(Long foodId) {
        this.foodItemRepository.deleteById(foodId);
        return;
    }
}