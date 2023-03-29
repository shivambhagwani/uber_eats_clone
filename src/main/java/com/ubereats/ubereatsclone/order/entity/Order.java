package com.ubereats.ubereatsclone.order.entity;

import com.ubereats.ubereatsclone.util.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order_table")
@Getter
@Setter
@NoArgsConstructor
public class Order extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderId;

    private Long customerId;

    private Long restaurantId;

    private OrderType orderType;
    @ElementCollection(targetClass = Long.class)
    private List<Long> foodIdsInOrder;
    private Integer itemCount;
    private Double deliveryFee;
    private BigDecimal totalPrice;
    private Integer eta;
    private OrderStatusEnum orderStatus;
    @Temporal(TemporalType.TIMESTAMP)
    Date orderDate = new Date();
}
