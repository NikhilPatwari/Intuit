package com.example.demo.controller.rest.advice;

import com.example.demo.dto.response.base_response.BaseResponse;
import com.example.demo.exception.AlreadyExistsException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.ServiceUnavailableException;
import com.example.demo.exception.ValidationFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.*;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<BaseResponse<?>> handleNotFoundException(NotFoundException ex) {
        BaseResponse<?> response = new BaseResponse<>("FAILURE", Collections.singletonList(ex.getErrorMessage()));
        return new ResponseEntity<>(response, ex.getHttpStatus());
    }

    @ExceptionHandler(value = AlreadyExistsException.class)
    public ResponseEntity<BaseResponse<?>> handleNotFoundException(AlreadyExistsException ex) {
        BaseResponse<?> response = new BaseResponse<>("FAILURE", Collections.singletonList(ex.getErrorMessage()));
        return new ResponseEntity<>(response, ex.getHttpStatus());
    }

    @ExceptionHandler(value = ServiceUnavailableException.class)
    public ResponseEntity<BaseResponse<?>> handleServiceUnavailableException(ServiceUnavailableException ex) {
        BaseResponse<?> response = new BaseResponse<>("FAILURE", Collections.singletonList(ex.getErrorMessage()));
        return new ResponseEntity<>(response, ex.getHttpStatus());
    }

    @ExceptionHandler(value = ValidationFailureException.class)
    public ResponseEntity<BaseResponse<?>> handleValidationFailureException(ValidationFailureException ex) {
        BaseResponse<?> response = new BaseResponse<>("FAILURE", Collections.singletonList(ex.getErrorMessage()));
        return new ResponseEntity<>(response, ex.getHttpStatus());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<?>> notValid(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));
        BaseResponse<?> response = new BaseResponse<>("FAILURE", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
