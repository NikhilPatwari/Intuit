package com.example.demo.dto.response.base_response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Body <T> {
    @JsonProperty("payload")
    private T payload;

    public Body (T payload) {
        this.payload = payload;
    }
}
