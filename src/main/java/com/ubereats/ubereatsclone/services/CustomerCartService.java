package com.ubereats.ubereatsclone.services;

import com.ubereats.ubereatsclone.entities.CustomerCart;

import java.util.List;

public interface CustomerCartService {

    CustomerCart createNewCart(CustomerCart cart);

    CustomerCart getCartById(Long cartId);

    void emptyCart(Long cartId);

}
