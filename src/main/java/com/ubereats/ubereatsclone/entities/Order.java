package com.ubereats.ubereatsclone.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order_table")
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderId;

    Long customerId;

    Long restaurantId;

    @ElementCollection(targetClass = Long.class)
    private List<Long> foodIdsInOrder;
    Integer itemCount;

    Double totalPrice;

    private OrderStatusEnum orderStatus;
    @Temporal(TemporalType.TIMESTAMP)
    Date orderDate = new Date();


}
