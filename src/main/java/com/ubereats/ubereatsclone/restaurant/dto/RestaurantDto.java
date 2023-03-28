package com.ubereats.ubereatsclone.restaurant.dto;

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
    private Double deliveryFee;
    private Double freeDeliveryAmount;
    private Double rating;
    private boolean operationStatus;

}