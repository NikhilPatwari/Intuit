package com.example.demo.controller.rest;

import com.example.demo.dto.response.base_response.BaseResponse;
import com.example.demo.models.BusinessProfile;
import com.example.demo.models.CreateProfileGrp;
import com.example.demo.models.UpdateProfileGrp;
import com.example.demo.service.BusinessProfileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/businessProfile")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BusinessProfileController {

    private final BusinessProfileManager service;

    @GetMapping("findById")
    public ResponseEntity<BaseResponse<BusinessProfile>> getBusinessProfileById(@RequestParam String id) {
        return ResponseEntity.ok(new BaseResponse<>(service.getBusinessProfileById(id), "SUCCESS"));
    }

    @PostMapping("create")
    public ResponseEntity<BaseResponse<Map<String, String>>> createBusinessProfile(@Validated(CreateProfileGrp.class) @RequestBody BusinessProfile profile, @RequestParam(required = false) String product) {
        return new ResponseEntity<>(new BaseResponse<>(service.createProfile(profile, product),"SUCCESS"), HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<BaseResponse<?>> updateBusinessProfile(@Validated(UpdateProfileGrp.class) @RequestBody BusinessProfile profile, @RequestParam(required = false) String product) {
        service.updateProfile(profile, product);
        return ResponseEntity.ok(new BaseResponse<>("SUCCESS"));
    }

    @DeleteMapping("delete")
    public ResponseEntity<BaseResponse<?>> deleteBusinessProfile(@RequestParam String id) {
        service.deleteProfile(id);
        return ResponseEntity.ok(new BaseResponse<>("SUCCESS"));
    }
}
