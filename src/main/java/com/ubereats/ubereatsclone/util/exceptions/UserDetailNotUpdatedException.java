package com.ubereats.ubereatsclone.util.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserDetailNotUpdatedException extends RuntimeException{

    String message;

    public UserDetailNotUpdatedException(String message) {
        super();
        log.error(message);
    }
}
