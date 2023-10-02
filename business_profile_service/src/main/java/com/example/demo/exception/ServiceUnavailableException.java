package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceUnavailableException extends RuntimeException{
    private final String errorMessage;
    private final HttpStatus httpStatus;
    public ServiceUnavailableException(String resourceName) {
        super(resourceName+ " is unavailable");
        this.errorMessage = "ERR503 : "+resourceName+ " is unavailable. Please try again after some time";
        this.httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
    }
}
