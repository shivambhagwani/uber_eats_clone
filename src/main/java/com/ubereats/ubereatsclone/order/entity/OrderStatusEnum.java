package com.ubereats.ubereatsclone.order.entity;

public enum OrderStatusEnum {
    SUBMITTED,
    PREPARING,
    READY,
    PICKED,
    DELIVERED,
    CANCELLED;

    private static final OrderStatusEnum[] vals = values();

    public OrderStatusEnum next() {
        if(this.ordinal() <= 4)
            return vals[this.ordinal() + 1 % vals.length];
        return null;
    }

    public OrderStatusEnum prev() {
        return vals[(this.ordinal() - 1) % vals.length];
    }
}
