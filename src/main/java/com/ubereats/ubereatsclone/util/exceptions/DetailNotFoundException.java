package com.ubereats.ubereatsclone.util.exceptions;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class DetailNotFoundException extends RuntimeException {

    String searchType;

    String searchBy;

    Long searchId;
    public DetailNotFoundException(String searchType, String searchBy, Long searchId) {
        super();
        log.error("The entity of type" + searchType + " searched using " +
                searchBy  +" specific to " + searchId + " was not found in the database");
    }
}
