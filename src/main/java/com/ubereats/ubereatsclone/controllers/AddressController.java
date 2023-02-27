package com.ubereats.ubereatsclone.controllers;


import com.ubereats.ubereatsclone.entities.CustomerAddress;
import com.ubereats.ubereatsclone.services.CustomerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/addresses")
public class AddressController {

    @Autowired
    CustomerAddressService customerAddressService;

    @GetMapping("/{addressId}")
    public CustomerAddress getAddressById(@PathVariable Long addressId) {
        CustomerAddress customerAddress = customerAddressService.getAddressById(addressId);

        return customerAddress;
    }
}
