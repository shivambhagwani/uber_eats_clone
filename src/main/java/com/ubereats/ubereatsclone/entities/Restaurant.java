package com.ubereats.ubereatsclone.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;

    private String restaurantName;
    private String cuisine;
    private String pincode;
    private Double rating;

    private boolean operationStatus; //Is restaurant serving for orders at the moment or not.

    public boolean getOperationStatus() {
        return this.operationStatus;
    }
}
