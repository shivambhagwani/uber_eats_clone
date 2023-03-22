package com.ubereats.ubereatsclone.customer.entity;

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
public class Customer extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String email;

    private String password;

    private String contactNumber;

    private String favCuisine;

    private String roles;

    @ElementCollection(targetClass = Long.class)
    private List<Long> favouriteRestaurants;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn = new Date();


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
