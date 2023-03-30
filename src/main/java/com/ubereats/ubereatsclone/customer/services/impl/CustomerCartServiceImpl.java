package com.ubereats.ubereatsclone.customer.services.impl;

import com.ubereats.ubereatsclone.customer.entity.CustomerCart;
import com.ubereats.ubereatsclone.util.exceptions.DetailNotFoundException;
import com.ubereats.ubereatsclone.customer.repository.CustomerCartRepository;
import com.ubereats.ubereatsclone.customer.services.CustomerCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerCartServiceImpl implements CustomerCartService {


    @Autowired
    CustomerCartRepository customerCartRepository;

    @Override
    public CustomerCart createNewCart(CustomerCart cart) {
        CustomerCart createdCart = this.customerCartRepository.save(cart);
        return createdCart;
    }

    //cache
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
