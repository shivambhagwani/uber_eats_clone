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
    private Long customerId;

    private String fullName;

    private String email;

    private String password;

    private String contactNumber;

    private String favCuisine;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "customer_cart_join_table",
        joinColumns = { @JoinColumn(name = "customer_id", referencedColumnName = "customerId") },
    inverseJoinColumns = { @JoinColumn(name = "cart_id", referencedColumnName = "cartId") })
    private CustomerCart customerCart;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "customer_address_join_table",
            joinColumns = { @JoinColumn(name = "customer_id", referencedColumnName = "customerId") },
            inverseJoinColumns = { @JoinColumn(name = "address_id", referencedColumnName = "addressId") })
    private CustomerAddress customerAddress;

}
