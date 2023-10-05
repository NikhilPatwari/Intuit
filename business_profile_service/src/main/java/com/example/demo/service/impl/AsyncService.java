package com.example.demo.service.impl;

import com.example.demo.dto.response.product_validation.ApprovalResponse;
import com.example.demo.models.BusinessProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AsyncService {
    private final RetryService retryService;

    @Async("threadPoolExecutor")
    public Future<ApprovalResponse> getApproval(BusinessProfile profile, String url) {
        log.debug("Inside async service get approval method");
        ApprovalResponse response = retryService.getApproval(profile, url);
        return new AsyncResult<>(response);
    }
}
