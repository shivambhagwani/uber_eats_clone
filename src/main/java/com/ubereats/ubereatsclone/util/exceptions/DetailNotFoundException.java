package com.ubereats.ubereatsclone.util.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DetailNotFoundException extends RuntimeException {

    String searchType;

    String searchBy;

    Long searchId;
    public DetailNotFoundException(String searchType, String searchBy, Long searchId) {
        super();
        System.out.println("The entity of type" + searchType + " searched using " +
                searchBy  +" specific to " + searchId + " was not found in the database");
    }
}
