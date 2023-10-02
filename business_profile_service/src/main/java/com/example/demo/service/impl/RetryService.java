package com.example.demo.service.impl;

import com.example.demo.dto.response.product_validation.ApprovalResponse;
import com.example.demo.dto.response.product_validation.ApprovalStatus;
import com.example.demo.models.BusinessProfile;
import com.example.demo.resource.BusinessProfileApprovalResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RetryService {
    private final BusinessProfileApprovalResource businessProfileApprovalResource;

    @Retryable(value = HttpServerErrorException.class, maxAttempts = 2, backoff = @Backoff(delay = 100))
    public ApprovalResponse getApproval(BusinessProfile profile, String url) {
        log.info("Inside retry Service. Call for Approval Service");
        ApprovalResponse response = businessProfileApprovalResource.getApproval(profile, url);
        if (response.getStatus()== ApprovalStatus.FAILED) {
            throw new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE, response.getErrors().toString());
        }
        return response;
    }

    @Recover
    public ApprovalResponse recover(HttpServerErrorException ex, BusinessProfile profile, String url) {
        log.info("Inside retry service. Recover method called");
        return ApprovalResponse.builder().status(ApprovalStatus.FAILED).errors(Collections.singletonList("Internal Server error")).build();
    }
}
