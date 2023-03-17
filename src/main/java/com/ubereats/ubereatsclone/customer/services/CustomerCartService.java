package com.ubereats.ubereatsclone.customer.services;

import com.ubereats.ubereatsclone.customer.entity.CustomerCart;

public interface CustomerCartService {

    CustomerCart createNewCart(CustomerCart cart);

    CustomerCart getCartById(Long cartId);

    void emptyCart(Long cartId);

}
