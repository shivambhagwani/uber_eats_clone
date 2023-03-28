package com.ubereats.ubereatsclone.restaurant.repository;

import com.ubereats.ubereatsclone.restaurant.dto.RestaurantEmployeeDto;
import com.ubereats.ubereatsclone.restaurant.entity.RestaurantEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantEmployeeRepository extends JpaRepository<RestaurantEmployee, Long> {

    List<RestaurantEmployeeDto> findByRestaurant(Long resId);

    RestaurantEmployee findByUsername(String email);
}
