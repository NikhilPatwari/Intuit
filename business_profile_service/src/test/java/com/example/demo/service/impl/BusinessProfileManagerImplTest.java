package com.example.demo.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.example.demo.exception.ValidationFailureException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

import java.util.*;

import com.example.demo.exception.AlreadyExistsException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.models.BusinessProfile;
import com.example.demo.repository.BusinessProfileRepository;

public class BusinessProfileManagerImplTest {

    @InjectMocks
    private BusinessProfileManagerImpl businessProfileManager;

    @Mock
    private BusinessProfileRepository repository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetBusinessProfileById() {
        String id = "testId";
        BusinessProfile expectedProfile = BusinessProfile.builder().build();
        expectedProfile.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(expectedProfile));
        BusinessProfile actualProfile = businessProfileManager.getBusinessProfileById(id);
        assertEquals(expectedProfile, actualProfile);
    }

    @Test(expected = NotFoundException.class)
    public void testGetBusinessProfileByIdNotFound() {
        String id = "testId";
        when(repository.findById(id)).thenReturn(Optional.empty());
        businessProfileManager.getBusinessProfileById(id);
    }

    @Test(expected = ValidationFailureException.class)
    public void getProfileExpectExceptionIfBlankID() {
        String id = " ";
        businessProfileManager.getBusinessProfileById(id);
    }

    @Test
    public void testCreateProfile() {
        BusinessProfile profile = BusinessProfile.builder().build();
        String product = "testProduct";
        String id = "testId";
        when(repository.countByPanOrEin(profile.getPan(), profile.getEin())).thenReturn(0l);
        when(repository.save(profile)).thenReturn(BusinessProfile.builder().id(id).build());
        Map<String, String> result = businessProfileManager.createProfile(profile, product);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(id, result.get("id"));
    }

    @Test(expected = ValidationFailureException.class)
    public void testCreateProfileThrowsExceptionIfIdNotBlank() {
        BusinessProfile profile = BusinessProfile.builder().id("abc").build();
        businessProfileManager.createProfile(profile, "qb");
    }

    @Test(expected = AlreadyExistsException.class)
    public void testCreateProfileAlreadyExists() {
        BusinessProfile profile = BusinessProfile.builder().pan("abc").ein("def").build();
        String product = "testProduct";
        when(repository.countByPanOrEin(profile.getPan(), profile.getEin())).thenReturn(1l);
        businessProfileManager.createProfile(profile, product);
    }

    @Test(expected = NotFoundException.class)
    public void updateProfileThrowsExceptionWhenProfileNotFound() {
        BusinessProfile profile = BusinessProfile.builder().id("ABC").pan("abc").ein("def").build();
        String product = "qb";
        when(repository.getProductsByProfileId(profile.getId())).thenReturn(Optional.empty());
        businessProfileManager.updateProfile(profile, product);
    }

    @Test(expected = ValidationFailureException.class)
    public void deleteProfileExpectExceptionIfBlankID() {
        String id = " ";
        businessProfileManager.deleteProfile(id);
    }
}
