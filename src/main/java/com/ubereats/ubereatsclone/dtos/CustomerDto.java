package com.ubereats.ubereatsclone.dtos;

import com.ubereats.ubereatsclone.entities.CustomerAddress;
import com.ubereats.ubereatsclone.entities.CustomerCart;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private CustomerCart customerCart;

    private CustomerAddress customerAddress;
}
