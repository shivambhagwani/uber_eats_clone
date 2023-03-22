package com.ubereats.ubereatsclone.customer.entity;

import com.ubereats.ubereatsclone.util.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "address")
@NoArgsConstructor
public class CustomerAddress extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    private String streetAddress;
    private String city;

    private String state;

    private String pincode;

}
