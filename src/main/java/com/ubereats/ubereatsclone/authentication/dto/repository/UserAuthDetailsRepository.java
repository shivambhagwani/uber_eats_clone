package com.ubereats.ubereatsclone.authentication.dto.repository;

import com.ubereats.ubereatsclone.authentication.dto.UserAuthDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthDetailsRepository extends JpaRepository<UserAuthDetails, Long> {

    public UserAuthDetails findByEmail(String email);
}
