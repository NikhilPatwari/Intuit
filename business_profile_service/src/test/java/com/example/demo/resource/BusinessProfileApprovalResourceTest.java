package com.example.demo.resource;

import com.example.demo.dto.response.product_validation.ApprovalResponse;
import com.example.demo.dto.response.product_validation.ApprovalStatus;
import com.example.demo.models.BusinessProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BusinessProfileApprovalResourceTest {


    @Mock
    private RestTemplate restTemplate;

    private BusinessProfileApprovalResource approvalResource;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        approvalResource = new BusinessProfileApprovalResource(restTemplate);
    }

    @Test
    public void testGetApproval_Success() {
        BusinessProfile profile = BusinessProfile.builder().build();
        String productUrl = "http://example.com/product";
        ApprovalResponse expectedResponse = ApprovalResponse.builder()
                .status(ApprovalStatus.APPROVED)
                .build();
        Mockito.when(restTemplate.postForObject(productUrl, profile, ApprovalResponse.class))
                .thenReturn(expectedResponse);
        ApprovalResponse actualResponse = approvalResource.getApproval(profile, productUrl);
        assertEquals(ApprovalStatus.APPROVED, actualResponse.getStatus());
    }

    @Test
    public void testGetApproval_HttpClientErrorException() {
        BusinessProfile profile = BusinessProfile.builder().build();
        String productUrl = "http://example.com/product";
        String errorMessage = "Client Error Message";
        HttpClientErrorException httpClientErrorException = new HttpClientErrorException(HttpStatus.BAD_REQUEST,errorMessage,errorMessage.getBytes(StandardCharsets.UTF_8), Charset.defaultCharset());
        Mockito.when(restTemplate.postForObject(productUrl, profile, ApprovalResponse.class))
                .thenThrow(httpClientErrorException);
        ApprovalResponse actualResponse = approvalResource.getApproval(profile, productUrl);
        assertEquals(ApprovalStatus.DECLINED, actualResponse.getStatus());
        assertEquals(Collections.singletonList(errorMessage), actualResponse.getErrors());
    }

    @Test
    public void testGetFallBackApproval() {
        BusinessProfile profile = BusinessProfile.builder().build();
        String productUrl = "http://example.com/product";
        ApprovalResponse actualResponse = approvalResource.getFallBackApproval(profile, productUrl);
        assertEquals(ApprovalStatus.FAILED, actualResponse.getStatus());
        assertEquals(Collections.singletonList("Service Unavailable"), actualResponse.getErrors());
    }
}
