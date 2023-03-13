package com.ubereats.ubereatsclone.controllers;

import com.ubereats.ubereatsclone.entities.CustomerCart;
import com.ubereats.ubereatsclone.services.CustomerCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/carts")
@Slf4j
public class CustomerCartController {

    @Autowired
    CustomerCartService customerCartService;

    @GetMapping("/{cartId}")
    public CustomerCart getCartById(@PathVariable Long cartId)
    {
        log.info("Cart {} was requested.", cartId);
        return this.customerCartService.getCartById(cartId);
    }
}
