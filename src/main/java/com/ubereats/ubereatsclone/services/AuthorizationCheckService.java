package com.ubereats.ubereatsclone.services;

import javax.servlet.http.HttpServletRequest;

public interface AuthorizationCheckService {

    boolean isRestaurantAdminContext(HttpServletRequest request);

    boolean isRestaurantChefContext(HttpServletRequest request);
}
