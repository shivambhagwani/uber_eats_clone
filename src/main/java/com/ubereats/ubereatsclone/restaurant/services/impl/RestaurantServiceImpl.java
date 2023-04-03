package com.ubereats.ubereatsclone.restaurant.services.impl;

import com.ubereats.ubereatsclone.order.repository.OrderRepository;
import com.ubereats.ubereatsclone.restaurant.dto.RestaurantDto;
import com.ubereats.ubereatsclone.food.entity.FoodItem;
import com.ubereats.ubereatsclone.restaurant.entity.Restaurant;
import com.ubereats.ubereatsclone.util.exceptions.DetailNotFoundException;
import com.ubereats.ubereatsclone.food.repository.FoodItemRepository;
import com.ubereats.ubereatsclone.restaurant.repository.RestaurantEmployeeRepository;
import com.ubereats.ubereatsclone.restaurant.repository.RestaurantRepository;
import com.ubereats.ubereatsclone.restaurant.services.RestaurantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = {"restaurant"})
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    FoodItemRepository foodItemRepository;

    @Autowired
    RestaurantEmployeeRepository restaurantEmployeeRepository;
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public RestaurantDto addNewRestaurant(RestaurantDto restaurantDto) {
        Restaurant res = this.modelMapper.map(restaurantDto, Restaurant.class);
        Restaurant resAdded = this.restaurantRepository.save(res);

        return this.modelMapper.map(resAdded, RestaurantDto.class);
    }

    @Override
    public Boolean restaurantExists(Long restaurantId) {
        return restaurantRepository.existsRestaurantByRestaurantId(restaurantId);
    }

    @Override
    @CacheEvict(allEntries = true)
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
    }

    @Override
    @Cacheable(sync = true)
    public void toggleRestaurantOperationStatus(Long restaurantId) {
        Restaurant res = this.restaurantRepository.findById(restaurantId).orElseThrow(() -> new DetailNotFoundException("Restaurant", "restaurantId", restaurantId));

        res.setOperationStatus(!res.getOperationStatus());

        this.restaurantRepository.save(res);
    }

    @Override
    @Cacheable(sync = true)
    public List<RestaurantDto> getAllRestaurants() {
        List<Restaurant> restaurants = this.restaurantRepository.findAll();

        List<RestaurantDto> restaurantDtos = restaurants
                                                .stream()
                                                .map(restaurant -> this.modelMapper.map(restaurant, RestaurantDto.class))
                                                .collect(Collectors.toList());

        return restaurantDtos;
    }

    @Override
    @Cacheable(sync = true)
    public RestaurantDto getRestaurantById(Long id) {
        Restaurant res = this.restaurantRepository.findById(id).orElseThrow(() -> new DetailNotFoundException("Restaurant", "restaurantId", id));
        return this.modelMapper.map(res, RestaurantDto.class);
    }

    @Override
    @Cacheable(sync = true)
    public List<RestaurantDto> getRestaurantsByCategory(String category) {
        List<Restaurant> restaurants = restaurantRepository.findByCategory(category);

        return restaurants.stream()
                        .map(restaurant -> this.modelMapper
                        .map(restaurant, RestaurantDto.class))
                        .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(allEntries = true)
    public RestaurantDto updateRestaurant(RestaurantDto updatedDetails, Long id) {
        Restaurant res = this.restaurantRepository.findById(id).orElseThrow(() -> new DetailNotFoundException("Restaurant", "restaurantId", id));

        res.setRestaurantName(updatedDetails.getRestaurantName());
        res.setRating(updatedDetails.getRating());
        res.setOperationStatus(updatedDetails.isOperationStatus());
        res.setPincode(updatedDetails.getPincode());
        res.setCuisine(updatedDetails.getCuisine());
        res.setCategories(updatedDetails.getCategories());
        res.setDeliveryFee(updatedDetails.getDeliveryFee());
        res.setFreeDeliveryAmount(updatedDetails.getFreeDeliveryAmount());

        Restaurant savedRestaurant = this.restaurantRepository.save(res);

        return this.modelMapper.map(savedRestaurant, RestaurantDto.class);
    }

    @Override
    @CachePut(key = "#restaurantId")
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
    @Cacheable
    public List<RestaurantDto> getRestaurantsByCuisine(String cuisine) {

        List<Restaurant> restaurants = restaurantRepository.findByCuisine(cuisine);

        return restaurants
                        .stream()
                        .map(res -> modelMapper
                        .map(res, RestaurantDto.class))
                        .collect(Collectors.toList());
    }

    @Override
    @Cacheable
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
    public List<Long> getPopularRestaurants() {
        Date startDate = new Date();
        Date oneDayBefore = new Date(startDate.getTime() - Duration.ofDays(1).toMillis());
        List<Object[]> populars = orderRepository.findPopularRestaurantsLastDay(oneDayBefore);

        List<Long> restaurantIds = new ArrayList<>();
        int i = 0;
        while (i < Math.min(populars.size(), 5)) {
            restaurantIds.add((Long)populars.get(i)[0]);
            i++;
        }
        return restaurantIds;
    }

//    @Override
//    public SecurityContext restaurantLogin(String email, String password) {
//        RestaurantEmployee restaurantEmployee = restaurantEmployeeRepository.findByEmail(email);
//        if(restaurantEmployee == null) {
//            throw new LoginFailedException("Employee details are wrong.");
//        } else if (password.equals(restaurantEmployee.getPhone())){
//            List<GrantedAuthority> authorities = new ArrayList<>();
//            if(restaurantEmployee.getAuthorities() == "ADMIN")
//                authorities.add(new SimpleGrantedAuthority("ADMIN"));
//            if(restaurantEmployee.getAuthorities() == "CHEF")
//                authorities.add(new SimpleGrantedAuthority("CHEF"));
//            Authentication authentication = new UsernamePasswordAuthenticationToken(email, password, authorities);
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            return SecurityContextHolder.getContext();
//        }
//        return null;
//    }
}