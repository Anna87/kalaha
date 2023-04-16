package com.bol.kalaha.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PlayerOrderException extends ResponseStatusException {
    public PlayerOrderException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
