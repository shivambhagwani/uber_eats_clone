package com.ubereats.ubereatsclone.customer.services;

import com.ubereats.ubereatsclone.customer.entity.CustomerAddress;

public interface CustomerAddressService {

    public CustomerAddress createNewAddress(CustomerAddress customerAddress);

    public CustomerAddress getAddressById(Long addressId);
}
