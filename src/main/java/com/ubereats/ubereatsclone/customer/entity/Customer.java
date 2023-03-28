package com.ubereats.ubereatsclone.customer.entity;

import com.ubereats.ubereatsclone.authentication.classes.User;
import com.ubereats.ubereatsclone.util.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
public class Customer extends User {

    private String fullName;

    private String contactNumber;

    private String favCuisine;


    @ElementCollection(targetClass = Long.class)
    private List<Long> favouriteRestaurants;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "customer_cart_join_table",
        joinColumns = { @JoinColumn(name = "customer_id", referencedColumnName = "id") },
    inverseJoinColumns = { @JoinColumn(name = "cart_id", referencedColumnName = "cartId") })
    private CustomerCart customerCart;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "customer_address_join_table",
            joinColumns = { @JoinColumn(name = "customer_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "address_id", referencedColumnName = "addressId") })
    private CustomerAddress customerAddress;

}
