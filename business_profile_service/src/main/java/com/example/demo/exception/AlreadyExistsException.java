package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AlreadyExistsException extends RuntimeException {
    private final String errorMessage;
    private final HttpStatus httpStatus;

    public AlreadyExistsException(String resourceName) {
        super(resourceName + " already exists");
        this.errorMessage = "ERR409 : " + resourceName + " already exist";
        this.httpStatus = HttpStatus.NOT_FOUND;
    }
}
