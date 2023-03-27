package com.ubereats.ubereatsclone.customer.repository;

import com.ubereats.ubereatsclone.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByUsername(String email);

    void deleteByUsername(String email);
}
