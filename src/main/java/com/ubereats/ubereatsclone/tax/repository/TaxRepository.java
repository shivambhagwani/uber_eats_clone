package com.ubereats.ubereatsclone.tax.repository;

import com.ubereats.ubereatsclone.tax.entity.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxRepository extends JpaRepository<Tax, String> {
    Tax findByPincode(String pincode);
}
