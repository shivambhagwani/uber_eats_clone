package com.ubereats.ubereatsclone.customer.services.impl;

import com.ubereats.ubereatsclone.customer.entity.CustomerAddress;
import com.ubereats.ubereatsclone.exceptions.DetailNotFoundException;
import com.ubereats.ubereatsclone.customer.repository.CustomerAddressRepository;
import com.ubereats.ubereatsclone.customer.services.CustomerAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class CustomerAddressServiceImpl implements CustomerAddressService {

    @Autowired
    CustomerAddressRepository customerAddressRepository;

    @Override
    public CustomerAddress createNewAddress(CustomerAddress customerAddress) {
        log.info("New address was stored for a customer.");
        CustomerAddress customerAddress1 = this.customerAddressRepository.save(customerAddress);

        return customerAddress1;
    }

    @Override
    public CustomerAddress getAddressById(Long addressId) {
        log.info("Address {} was requested.", addressId);
        CustomerAddress customerAddress = this.customerAddressRepository.findById(addressId).orElseThrow(() -> new DetailNotFoundException("Address", "addressId", addressId));

        return customerAddress;
    }
}
