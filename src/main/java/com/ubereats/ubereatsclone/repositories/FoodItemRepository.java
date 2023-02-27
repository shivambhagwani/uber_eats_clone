package com.ubereats.ubereatsclone.repositories;

import com.ubereats.ubereatsclone.entities.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

}
