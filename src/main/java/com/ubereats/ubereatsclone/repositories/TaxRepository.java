package com.ubereats.ubereatsclone.repositories;

import com.ubereats.ubereatsclone.entities.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxRepository extends JpaRepository<Tax, String> {
    Tax findByPincode(String pincode);
}
