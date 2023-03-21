package com.ubereats.ubereatsclone.util;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        if(SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            return "UBER_ADMIN".describeConstable();
        } else {
            return SecurityContextHolder.getContext().getAuthentication().getName().describeConstable();
        }
    }
}
