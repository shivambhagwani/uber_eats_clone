package com.ubereats.ubereatsclone.exceptions;

import org.apache.catalina.User;

public class UserDetailNotUpdatedException extends RuntimeException{

    String message;

    public UserDetailNotUpdatedException(String message) {
        super();
        System.out.println(message);
    }
}
