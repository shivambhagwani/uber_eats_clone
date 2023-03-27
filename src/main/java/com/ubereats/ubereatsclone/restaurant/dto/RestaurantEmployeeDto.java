package com.ubereats.ubereatsclone.restaurant.dto;

import com.ubereats.ubereatsclone.restaurant.entity.Restaurant;
import com.ubereats.ubereatsclone.restaurant.entity.RestaurantEmployeeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RestaurantEmployeeDto {

    private String name;
    private Integer age;

    private String phone;

    private String email;
    private String username;
    private String password;

    private String authorities;
    private Restaurant restaurant;

}
