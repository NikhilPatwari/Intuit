package com.example.demo.service;

import com.example.demo.models.BusinessProfile;

import java.util.Map;

public interface BusinessProfileManager {
    BusinessProfile getBusinessProfileById(String id);

    void deleteProfile(String id);

    Map<String, String> createProfile(BusinessProfile profile, String product);

    void updateProfile(BusinessProfile profile, String product);
}
