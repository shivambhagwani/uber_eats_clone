package com.ubereats.ubereatsclone.customer.repository;

import com.ubereats.ubereatsclone.customer.entity.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long> {

}
