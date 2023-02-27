package com.ubereats.ubereatsclone.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer_cart")
@Getter
@Setter
@NoArgsConstructor
public class CustomerCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    Long customerId;
    //TODO - Add Items here.
    //Only storing foodIds to save space. Food Items can be fetched later for calculating bills.

    @ElementCollection(targetClass = Long.class)
    private List<Long> foodIdsInCart = new ArrayList<>();

}
