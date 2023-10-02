package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ValidationFailureException extends RuntimeException {
    private final String errorMessage;
    private final HttpStatus httpStatus;
    public ValidationFailureException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = "ERR400 : "+ errorMessage;
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }
}
