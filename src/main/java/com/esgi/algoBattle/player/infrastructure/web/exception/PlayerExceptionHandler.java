package com.esgi.algoBattle.player.infrastructure.web.exception;


import com.esgi.algoBattle.player.domain.exception.GameOverException;
import com.esgi.algoBattle.player.domain.exception.TooManyPlayersInGameException;
import com.esgi.algoBattle.player.domain.exception.TooManyPlayersWonException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class PlayerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TooManyPlayersInGameException.class)
    public ResponseEntity<String> on(TooManyPlayersInGameException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TooManyPlayersWonException.class)
    public ResponseEntity<String> on(TooManyPlayersWonException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GameOverException.class)
    public ResponseEntity<String> on(GameOverException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
