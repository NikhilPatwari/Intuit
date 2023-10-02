package com.example.demo.service.impl;

import com.example.demo.dto.response.product_validation.ApprovalResponse;
import com.example.demo.dto.response.product_validation.ApprovalStatus;
import com.example.demo.models.BusinessProfile;
import com.example.demo.resource.BusinessProfileApprovalResource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RetryServiceTest {

    @Mock
    private BusinessProfileApprovalResource businessProfileApprovalResource;

    private RetryService retryService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        retryService = new RetryService(businessProfileApprovalResource);
    }

    @Test
    public void testGetApproval_Success() {
        BusinessProfile profile = BusinessProfile.builder().build();
        String url = "testUrl";
        ApprovalResponse expectedResponse = new ApprovalResponse(ApprovalStatus.APPROVED, null);
        when(businessProfileApprovalResource.getApproval(profile, url)).thenReturn(expectedResponse);
        ApprovalResponse actualResponse = retryService.getApproval(profile, url);
        assertEquals(expectedResponse, actualResponse);
        verify(businessProfileApprovalResource, times(1)).getApproval(profile, url);
    }

    @Test
    public void testRecover() {
        BusinessProfile profile = BusinessProfile.builder().build();
        String url = "testUrl";
        HttpServerErrorException exception = new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);

        ApprovalResponse actualResponse = retryService.recover(exception, profile, url);

        assertEquals(ApprovalStatus.FAILED, actualResponse.getStatus());
        assertEquals(Collections.singletonList("Internal Server error"), actualResponse.getErrors());
    }
}
