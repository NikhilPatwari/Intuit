package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends RuntimeException {
    private final String errorMessage;
    private final HttpStatus httpStatus;
    public NotFoundException(String resourceName) {
        super(resourceName+ " not found");
        this.errorMessage = "ERR404 : "+resourceName+ " not found";
        this.httpStatus = HttpStatus.NOT_FOUND;
    }
}
