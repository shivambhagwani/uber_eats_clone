package com.ubereats.ubereatsclone.repositories;

import com.ubereats.ubereatsclone.dtos.CustomerDto;
import com.ubereats.ubereatsclone.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {


//    CustomerDto findCustomerByEmail(String emailId);
}
