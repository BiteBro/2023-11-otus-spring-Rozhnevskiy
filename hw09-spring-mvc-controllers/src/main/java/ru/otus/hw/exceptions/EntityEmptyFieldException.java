package ru.otus.hw.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EntityEmptyFieldException extends RuntimeException {

    public EntityEmptyFieldException(String message) {
        super(message);
    }
}
