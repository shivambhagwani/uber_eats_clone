package com.ubereats.ubereatsclone.services.impl;

import com.ubereats.ubereatsclone.entities.CustomerAddress;
import com.ubereats.ubereatsclone.exceptions.DetailNotFoundException;
import com.ubereats.ubereatsclone.repositories.CustomerAddressRepository;
import com.ubereats.ubereatsclone.services.CustomerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CustomerAddressServiceImpl implements CustomerAddressService {

    @Autowired
    CustomerAddressRepository customerAddressRepository;

    @Override
    public CustomerAddress createNewAddress(CustomerAddress customerAddress) {
        CustomerAddress customerAddress1 = this.customerAddressRepository.save(customerAddress);

        return customerAddress1;
    }

    @Override
    public CustomerAddress getAddressById(Long addressId) {
        CustomerAddress customerAddress = this.customerAddressRepository.findById(addressId).orElseThrow(() -> new DetailNotFoundException("Address", "addressId", addressId));

        return customerAddress;
    }
}
