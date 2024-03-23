package ru.otus.hw.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.otus.hw.exceptions.EntityEmptyFieldException;
import ru.otus.hw.exceptions.EntityNotFoundException;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(EntityEmptyFieldException.class)
    public ResponseEntity<String> handleEntityEmptyFieldException(EntityEmptyFieldException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

}
