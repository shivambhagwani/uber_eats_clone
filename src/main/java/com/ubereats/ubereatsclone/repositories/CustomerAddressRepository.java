package com.ubereats.ubereatsclone.repositories;

import com.ubereats.ubereatsclone.entities.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long> {

}
