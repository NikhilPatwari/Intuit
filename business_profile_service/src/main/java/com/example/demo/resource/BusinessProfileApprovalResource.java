package com.example.demo.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.dto.response.product_validation.ApprovalResponse;
import com.example.demo.dto.response.product_validation.ApprovalStatus;
import com.example.demo.models.BusinessProfile;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BusinessProfileApprovalResource {
    private final RestTemplate restTemplate;

    @HystrixCommand(
            fallbackMethod = "getFallBackApproval",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            }
    )
    public ApprovalResponse getApproval(BusinessProfile profile, String productUrl) {
        log.info("sending approval request");
        try {
            return restTemplate.postForObject(productUrl, profile, ApprovalResponse.class);
        } catch (HttpClientErrorException ex) {
            return ApprovalResponse.builder().status(ApprovalStatus.DECLINED).errors(Collections.singletonList(ex.getResponseBodyAsString())).build();
        }
    }

    public ApprovalResponse getFallBackApproval(BusinessProfile profile, String productUrl) {
        return ApprovalResponse.builder().status(ApprovalStatus.FAILED).errors(Collections.singletonList("Service Unavailable")).build();
    }
}
