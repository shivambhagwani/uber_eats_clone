package com.ubereats.ubereatsclone.dtos;

import com.ubereats.ubereatsclone.entities.Restaurant;
import com.ubereats.ubereatsclone.entities.RestaurantEmployeeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RestaurantEmployeeDto {

    private String name;
    private Integer age;

    private Restaurant restaurant;

    private RestaurantEmployeeEnum jobRole;
}
