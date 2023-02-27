package com.ubereats.ubereatsclone.services;

import com.ubereats.ubereatsclone.entities.CustomerAddress;

public interface CustomerAddressService {

    public CustomerAddress createNewAddress(CustomerAddress customerAddress);

    public CustomerAddress getAddressById(Long addressId);
}
