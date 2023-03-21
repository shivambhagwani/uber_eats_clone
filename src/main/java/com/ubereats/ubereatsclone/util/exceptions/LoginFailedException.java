package com.ubereats.ubereatsclone.util.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginFailedException extends RuntimeException {

    public LoginFailedException(String message) {
        super();
        log.error(message);
    }
}
