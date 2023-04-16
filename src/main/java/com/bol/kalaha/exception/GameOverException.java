package com.bol.kalaha.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GameOverException extends ResponseStatusException {
    public GameOverException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
