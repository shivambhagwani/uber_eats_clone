package com.ubereats.ubereatsclone.services;

import com.ubereats.ubereatsclone.entities.CustomerCart;

import java.util.List;

public interface CustomerCartService {

    public CustomerCart createNewCart(CustomerCart cart);

    public CustomerCart getCartById(Long cartId);

    //TODO - Implement Menu, Item and Restaurant to come back to Cart so that Items from restaurants can be added to the cart by customer.

    //TODO - Place Order. Removes items from cart and adds it to Customer's order history.

//    public List<CustomerCart> getAllCarts();
}
