package com.ubereats.ubereatsclone.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "customer_cart")
@Getter
@Setter
@NoArgsConstructor
public class CustomerCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long cartId;

    String tryOutString;

    //TODO - Add Items here.

//    @OneToOne
//    @MapsId
//    @JoinColumn(name = "customer_id")
//    private Customer customer;


}
