package com.bol.kalaha.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmptyCellException extends ResponseStatusException {
    public EmptyCellException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
