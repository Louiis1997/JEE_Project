package com.esgi.algoBattle.resolution.domain.exception;

public class NoCurrentPlayerException extends RuntimeException {
    public NoCurrentPlayerException(String message) {
        super(message);
    }
}
