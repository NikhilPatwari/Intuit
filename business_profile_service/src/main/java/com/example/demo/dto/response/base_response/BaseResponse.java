package com.example.demo.dto.response.base_response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BaseResponse<T> {
    @JsonProperty("body")
    private Body<T> body;

    @JsonProperty("status")
    private final String status;

    @JsonProperty("errors")
    private List<String> errors;

    public BaseResponse(T body, String status) {
        this.body = new Body<>(body);
        this.status = status;
    }

    public BaseResponse(String status) {
        this.status = status;
    }

    public BaseResponse(String status, List<String> errors) {
        this.status = status;
        this.errors = errors;
    }
}
