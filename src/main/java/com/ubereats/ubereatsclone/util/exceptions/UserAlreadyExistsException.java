package com.ubereats.ubereatsclone.util.exceptions;

public class UserAlreadyExistsException extends RuntimeException{

    String message;

    public UserAlreadyExistsException(String message) {
        super();
        System.out.println(message);
    }
}
