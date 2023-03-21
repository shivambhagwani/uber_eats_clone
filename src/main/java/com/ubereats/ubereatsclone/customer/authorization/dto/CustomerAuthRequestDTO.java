package com.ubereats.ubereatsclone.customer.authorization.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerAuthRequestDTO {

    String email;
    String password;
}
