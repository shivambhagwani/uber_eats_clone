package com.ubereats.ubereatsclone.services;

import com.ubereats.ubereatsclone.entities.CustomerCart;

import java.util.List;

public interface CustomerCartService {

    public CustomerCart createNewCart();

    public CustomerCart getCartById(Long cartId);

    //TODO - Implement Menu, Item and Restaurant to come back to Cart so that Items from restaurants can be added to the cart by customer.

//    public List<CustomerCart> getAllCarts();
}
