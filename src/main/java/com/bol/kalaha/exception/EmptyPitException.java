package com.bol.kalaha.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmptyPitException extends ResponseStatusException {
    public EmptyPitException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
