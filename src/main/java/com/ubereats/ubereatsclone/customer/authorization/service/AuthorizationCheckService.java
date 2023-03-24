package com.ubereats.ubereatsclone.customer.authorization.service;

import javax.servlet.http.HttpServletRequest;

public interface AuthorizationCheckService {

    boolean isRestaurantAdminContext(HttpServletRequest request);
}
