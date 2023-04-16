package com.bol.kalaha.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BoardNotFoundException extends ResponseStatusException {

    public BoardNotFoundException(String message){
        super(HttpStatus.NOT_FOUND, message);
    }
}
