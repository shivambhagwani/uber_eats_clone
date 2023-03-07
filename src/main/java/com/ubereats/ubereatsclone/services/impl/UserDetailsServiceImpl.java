package com.ubereats.ubereatsclone.services.impl;

import com.ubereats.ubereatsclone.entities.User;
import com.ubereats.ubereatsclone.repositories.UserRepository;
import com.ubereats.ubereatsclone.utils.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("Could not fins user.");
        }

        return new MyUserDetails(user);
    }
}
