package com.ubereats.ubereatsclone.entities;

public enum OrderStatusEnum {
    SUBMITTED,
    PREPARING,
    READY,
    PICKED,
    DELIVERED;

    private static final OrderStatusEnum[] vals = values();

    public OrderStatusEnum next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }

    public OrderStatusEnum prev() {
        return vals[(this.ordinal() - 1) % vals.length];
    }
}
