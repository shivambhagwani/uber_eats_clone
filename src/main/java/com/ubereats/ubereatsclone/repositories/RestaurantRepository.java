package com.ubereats.ubereatsclone.repositories;

import com.ubereats.ubereatsclone.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByCuisine(String cuisine);

}
