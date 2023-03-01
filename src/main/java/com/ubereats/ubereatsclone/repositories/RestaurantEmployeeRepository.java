package com.ubereats.ubereatsclone.repositories;

import com.ubereats.ubereatsclone.entities.RestaurantEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantEmployeeRepository extends JpaRepository<RestaurantEmployee, Long> {

}
