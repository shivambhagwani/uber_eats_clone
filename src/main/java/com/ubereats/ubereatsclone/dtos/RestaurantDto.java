package com.ubereats.ubereatsclone.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RestaurantDto {
    private Long restaurantId;
    private String restaurantName;
    private String cuisine;
    private String pincode;
    private Double rating;
    private boolean operationStatus;
//    private String menuId;
}