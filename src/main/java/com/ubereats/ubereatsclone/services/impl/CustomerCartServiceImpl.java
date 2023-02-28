package com.ubereats.ubereatsclone.services.impl;

import com.ubereats.ubereatsclone.entities.CustomerCart;
import com.ubereats.ubereatsclone.exceptions.DetailNotFoundException;
import com.ubereats.ubereatsclone.repositories.CustomerCartRepository;
import com.ubereats.ubereatsclone.services.CustomerCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerCartServiceImpl implements CustomerCartService {


    @Autowired
    CustomerCartRepository customerCartRepository;

    @Override
    public CustomerCart createNewCart(CustomerCart cart) {
        CustomerCart createdCart = this.customerCartRepository.save(cart);
        return createdCart;
    }

    @Override
    public CustomerCart getCartById(Long cartId) {
        CustomerCart cart = this.customerCartRepository.findById(cartId).orElseThrow(() -> new DetailNotFoundException("CustomerCart", "cartId", cartId));
        return cart;
    }

    @Override
    public void emptyCart(Long cartId) {
        CustomerCart customerCart = customerCartRepository.findById(cartId).orElseThrow();
        customerCart.getFoodIdsInCart().clear();
        customerCartRepository.save(customerCart);
    }


}
