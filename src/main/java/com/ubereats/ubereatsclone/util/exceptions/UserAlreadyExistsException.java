package com.ubereats.ubereatsclone.util.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAlreadyExistsException extends RuntimeException{

    String message;

    public UserAlreadyExistsException(String message) {
        super();
        log.error(message);
    }
}
