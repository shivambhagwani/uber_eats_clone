package com.ubereats.ubereatsclone.customer.repository;

import com.ubereats.ubereatsclone.customer.entity.CustomerCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerCartRepository extends JpaRepository<CustomerCart, Long> {

}
