package com.ubereats.ubereatsclone.repositories;

import com.ubereats.ubereatsclone.dtos.RestaurantEmployeeDto;
import com.ubereats.ubereatsclone.entities.RestaurantEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantEmployeeRepository extends JpaRepository<RestaurantEmployee, Long> {

    List<RestaurantEmployeeDto> findByRestaurant(Long resId);

    RestaurantEmployee findByEmail(String email);
}
