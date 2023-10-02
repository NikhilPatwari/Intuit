package com.example.demo.dto.response.product_validation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalResponse {

    @JsonProperty("status")
    private ApprovalStatus status;

    @JsonProperty("errors")
    private List<String> errors;
}

