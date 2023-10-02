package com.example.demo.controller.rest;

import com.example.demo.dto.request.business_profile.BusinessProfile;
import com.example.demo.dto.response.approval_response.ApprovalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/businessProfile")
@Slf4j
public class BusinessProfileController {

    @PostMapping("validate")
    public ResponseEntity<ApprovalResponse> validateBusinessProfile(@RequestBody BusinessProfile profile) {
        log.info("Validate API called");
        return new ResponseEntity<>(
                ApprovalResponse.builder()
                        .status("APPROVED")
//                        .errors(Collections.singletonList("Duplicate Email Found"))
                        .build(), HttpStatus.OK
        );
    }
}
