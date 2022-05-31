package com.esgi.algoBattle.player.domain.exception;

public class TooManyPlayersInGameException extends RuntimeException {
    public TooManyPlayersInGameException(String message) {
        super(message);
    }
}
