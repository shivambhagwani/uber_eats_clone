package com.ubereats.ubereatsclone.repositories;

import com.ubereats.ubereatsclone.entities.CustomerCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerCartRepository extends JpaRepository<CustomerCart, Long> {

}
