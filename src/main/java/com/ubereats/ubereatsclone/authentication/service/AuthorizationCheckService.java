package com.ubereats.ubereatsclone.authentication.service;

import javax.servlet.http.HttpServletRequest;

public interface AuthorizationCheckService {

    boolean isRestaurantAdminContext(HttpServletRequest request);
}
