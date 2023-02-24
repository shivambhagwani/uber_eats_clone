package com.ubereats.ubereatsclone.controllers;

import com.ubereats.ubereatsclone.entities.CustomerCart;
import com.ubereats.ubereatsclone.services.CustomerCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/carts")
public class CustomerCartController {

    @Autowired
    CustomerCartService customerCartService;

    @GetMapping("/{cartId}")
    public CustomerCart getCartById(@PathVariable Long cartId) {
        return this.customerCartService.getCartById(cartId);
    }

//    @GetMapping("/")
//    public List<CustomerCart> getAllCarts() {
//        return this.customerCartService.getAllCarts();
//    }


}
