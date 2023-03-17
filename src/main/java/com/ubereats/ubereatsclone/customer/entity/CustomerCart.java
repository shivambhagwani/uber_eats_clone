package com.ubereats.ubereatsclone.customer.entity;

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


    @ElementCollection(targetClass = Long.class)
    private List<Long> foodIdsInCart = new ArrayList<>();
}
