package com.ubereats.ubereatsclone.util.exceptions;

import org.apache.catalina.User;

public class UserDetailNotUpdatedException extends RuntimeException{

    String message;

    public UserDetailNotUpdatedException(String message) {
        super();
        System.out.println(message);
    }
}
