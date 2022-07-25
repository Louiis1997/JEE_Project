package com.esgi.algoBattle.resolution.infrastructure.web.exception;

import com.esgi.algoBattle.game.domain.exception.IncorrectPlayerNumberException;
import com.esgi.algoBattle.resolution.domain.exception.ComplexitiesDoNotMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class ResolutionExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IncorrectPlayerNumberException.class)
    public ResponseEntity<String> on(IncorrectPlayerNumberException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ComplexitiesDoNotMatchException.class)
    public ResponseEntity<String> on(ComplexitiesDoNotMatchException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }
}
