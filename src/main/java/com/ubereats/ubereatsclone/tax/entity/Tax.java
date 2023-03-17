package com.ubereats.ubereatsclone.tax.entity;


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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer taxId;

    private String pincode;

    @Column(name = "tax_rate")
    private double tax;

}
