package com.ubereats.ubereatsclone.restaurant.repository;

import com.ubereats.ubereatsclone.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByCuisine(String cuisine);
    List<Restaurant> findByRestaurantNameLike(String nameWithWildcard);

}
