package com.example.demo.dto.response.approval_response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public class ApprovalResponse {
    @JsonProperty("status")
    private String status;

    @JsonProperty("errors")
    private List<String> errors;
}
