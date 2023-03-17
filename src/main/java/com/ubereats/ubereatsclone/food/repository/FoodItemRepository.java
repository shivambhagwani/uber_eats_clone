package com.ubereats.ubereatsclone.food.repository;

import com.ubereats.ubereatsclone.food.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

}
