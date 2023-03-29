package com.ubereats.ubereatsclone.restaurant.entity;

import com.ubereats.ubereatsclone.util.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor
public class Restaurant extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;
    private String restaurantName;
    @ElementCollection
    private List<String> categories = new ArrayList<>();
    private String cuisine;
    private String pincode;
    private Double deliveryFee;
    private Double freeDeliveryAmount;
    private Double rating;
    private boolean operationStatus; //Is restaurant serving for orders at the moment or not.
    public boolean getOperationStatus() {
        return this.operationStatus;
    }
}
