package com.ubereats.ubereatsclone.customer.dto;

import com.ubereats.ubereatsclone.customer.entity.CustomerAddress;
import com.ubereats.ubereatsclone.customer.entity.CustomerCart;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CustomerDto {
    private Long customerId;

    private String fullName;

    private String email;

    private String password;

    private String contactNumber;

    private String favCuisine;
    private List<Long> favouriteRestaurants;

    private CustomerCart customerCart;

    private CustomerAddress customerAddress;
}
