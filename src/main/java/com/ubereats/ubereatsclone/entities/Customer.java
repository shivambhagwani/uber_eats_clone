package com.ubereats.ubereatsclone.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customerId;

    private String fullName;

    private String email;

    private String password;

    private String contactNumber;

    private String favCuisine;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_cart_id")
    private CustomerCart customerCart;

}
