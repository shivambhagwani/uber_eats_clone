package com.ubereats.ubereatsclone.customer.dto;

import com.ubereats.ubereatsclone.customer.entity.CustomerAddress;
import com.ubereats.ubereatsclone.customer.entity.CustomerCart;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CustomerDto {
    private Long id;

    private String fullName;

    private String username;

    private String password;

    private String contactNumber;

    private String favCuisine;

    private String roles;
    private List<Long> favouriteRestaurants;

    private CustomerCart customerCart;

    private CustomerAddress customerAddress;
    private Boolean uberOneMember;
    private LocalDateTime uberOneFrom;
    private LocalDateTime uberOneUntil;
}
