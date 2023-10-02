package com.example.demo.service.impl;

import com.example.demo.dto.response.product_validation.ApprovalResponse;
import com.example.demo.models.BusinessProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AsyncService {
    private final RetryService retryService;

    @Async
    public Future<ApprovalResponse> getApproval(BusinessProfile profile, String url) {
        ApprovalResponse response = retryService.getApproval(profile, url);
        return new AsyncResult<>(response);
    }
}
