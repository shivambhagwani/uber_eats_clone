package com.ubereats.ubereatsclone.services;

import com.ubereats.ubereatsclone.entities.CustomerCart;

import java.util.List;

public interface CustomerCartService {

    public CustomerCart createNewCart(CustomerCart cart);

    public CustomerCart getCartById(Long cartId);

    //TODO - Place Order. Removes items from cart and adds it to Customer's order history.

//    public List<CustomerCart> getAllCarts();
}
