package com.ubereats.ubereatsclone.util.exceptions;

public class LoginFailedException extends RuntimeException {

    public LoginFailedException(String message) {
        super();
        System.out.println(message);
    }
}
