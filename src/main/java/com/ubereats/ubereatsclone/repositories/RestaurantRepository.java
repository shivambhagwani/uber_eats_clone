package com.ubereats.ubereatsclone.repositories;

import com.ubereats.ubereatsclone.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}
