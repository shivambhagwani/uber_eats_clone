package com.ubereats.ubereatsclone.authorization.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCredentialDTO {

    String email;
    String password;
}
