package com.ubereats.ubereatsclone.services.impl;

import com.ubereats.ubereatsclone.dtos.RestaurantDto;
import com.ubereats.ubereatsclone.entities.FoodItem;
import com.ubereats.ubereatsclone.entities.Restaurant;
import com.ubereats.ubereatsclone.entities.RestaurantEmployee;
import com.ubereats.ubereatsclone.entities.RestaurantEmployeeEnum;
import com.ubereats.ubereatsclone.exceptions.DetailNotFoundException;
import com.ubereats.ubereatsclone.exceptions.LoginFailedException;
import com.ubereats.ubereatsclone.repositories.FoodItemRepository;
import com.ubereats.ubereatsclone.repositories.RestaurantEmployeeRepository;
import com.ubereats.ubereatsclone.repositories.RestaurantRepository;
import com.ubereats.ubereatsclone.services.RestaurantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    RestaurantEmployeeRepository restaurantEmployeeRepository;

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

        try {
            List<FoodItem> foodItemsBelongingToThisRestaurant = getFoodItemsByRestaurantId(restaurantId);
            for (FoodItem f : foodItemsBelongingToThisRestaurant) {
                this.foodItemRepository.deleteById(f.getItemId());
            }
            this.restaurantRepository.deleteById(restaurantId);
        } catch (Exception e) {
            throw new DetailNotFoundException("Restaurant ID Not Found", "restaurantId", restaurantId);
        }
        return;
    }

    @Override
    public void toggleRestaurantOperationStatus(Long restaurantId) {
        Restaurant res = this.restaurantRepository.findById(restaurantId).orElseThrow(() -> new DetailNotFoundException("Restaurant", "restaurantId", restaurantId));

        res.setOperationStatus(!res.getOperationStatus());

        this.restaurantRepository.save(res);

        return;

    }


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

    @Override
    public List<RestaurantDto> getRestaurantsByCuisine(String cuisine) {

        List<Restaurant> restaurants = restaurantRepository.findByCuisine(cuisine);

        return restaurants
                        .stream()
                        .map(res -> modelMapper
                        .map(res, RestaurantDto.class))
                        .collect(Collectors.toList());
    }

    @Override
    public List<RestaurantDto> getRestaurantsByName(String name) {
        String wildcard = "%" + name + "%";
        List<Restaurant> restaurants = restaurantRepository.findByRestaurantNameLike(wildcard);

        return restaurants
                .stream()
                .map(res -> modelMapper
                        .map(res, RestaurantDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public SecurityContext restaurantLogin(String email, String password) {
        RestaurantEmployee restaurantEmployee = restaurantEmployeeRepository.findByEmail(email);
        if(restaurantEmployee == null) {
            throw new LoginFailedException("Employee details are wrong.");
        } else if (password.equals(restaurantEmployee.getPhone())){
            List<GrantedAuthority> authorities = new ArrayList<>();
            if(restaurantEmployee.getJobRole() == RestaurantEmployeeEnum.ADMIN)
                authorities.add(new SimpleGrantedAuthority("ADMIN"));
            if(restaurantEmployee.getJobRole() == RestaurantEmployeeEnum.CHEF)
                authorities.add(new SimpleGrantedAuthority("CHEF"));
            Authentication authentication = new UsernamePasswordAuthenticationToken(email, password, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return SecurityContextHolder.getContext();
        }
        return null;
    }
}