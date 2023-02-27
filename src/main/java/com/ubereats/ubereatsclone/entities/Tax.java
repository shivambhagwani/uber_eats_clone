package com.ubereats.ubereatsclone.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Tax {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String pincode;

    @Column(name = "tax_rate")
    private double tax;

    //TODO - To work on tax controller, service.
}
