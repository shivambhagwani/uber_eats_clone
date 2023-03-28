package com.ubereats.ubereatsclone.restaurant.entity;

import com.ubereats.ubereatsclone.authentication.classes.User;
import com.ubereats.ubereatsclone.util.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class RestaurantEmployee extends User {

    @OneToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    private String phone;

    private String fullName;
    private Integer age;

}
