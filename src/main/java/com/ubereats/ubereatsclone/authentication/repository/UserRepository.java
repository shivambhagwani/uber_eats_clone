package com.ubereats.ubereatsclone.authentication.repository;


import com.ubereats.ubereatsclone.authentication.classes.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
