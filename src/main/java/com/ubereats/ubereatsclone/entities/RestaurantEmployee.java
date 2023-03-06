package com.ubereats.ubereatsclone.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Optional;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class RestaurantEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private RestaurantEmployeeEnum jobRole;

    private String name;
    private Integer age;

}
