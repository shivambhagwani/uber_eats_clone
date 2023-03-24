package com.ubereats.ubereatsclone.customer.authorization.service.impl;

import com.ubereats.ubereatsclone.customer.authorization.service.AuthorizationCheckService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthorizationCheckImpl implements AuthorizationCheckService {
    @Override
    public boolean isRestaurantAdminContext(HttpServletRequest request) {
        SecurityContext context = (SecurityContext) request.getSession().getAttribute("context");
        if(context != null && context.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
            return true;
        else
            return false;
    }
}
