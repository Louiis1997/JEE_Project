package com.esgi.algoBattle.player.domain.exception;

public class TooManyPlayersWonException extends RuntimeException {
    public TooManyPlayersWonException(String message) {
        super(message);
    }
}
