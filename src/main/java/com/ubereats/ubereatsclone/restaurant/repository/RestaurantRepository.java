package com.ubereats.ubereatsclone.restaurant.repository;

import com.ubereats.ubereatsclone.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByCuisine(String cuisine);
    List<Restaurant> findByRestaurantNameLike(String nameWithWildcard);
    Boolean existsRestaurantByRestaurantId(Long restaurantId);

    @Query(value = "SELECT r FROM Restaurant r WHERE :category MEMBER OF r.categories")
    List<Restaurant> findByCategory(@Param("category") String category);

}
