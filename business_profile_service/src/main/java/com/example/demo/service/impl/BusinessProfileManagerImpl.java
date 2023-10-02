package com.example.demo.service.impl;

import com.example.demo.dto.response.product_validation.ApprovalResponse;
import com.example.demo.dto.response.product_validation.ApprovalStatus;
import com.example.demo.exception.AlreadyExistsException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.ServiceUnavailableException;
import com.example.demo.exception.ValidationFailureException;
import com.example.demo.models.BusinessProfile;
import com.example.demo.repository.BusinessProfileRepository;
import com.example.demo.service.BusinessProfileManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BusinessProfileManagerImpl implements BusinessProfileManager {
    private final BusinessProfileRepository repository;
    private final AsyncService asyncService;
    private final Environment env;

    @Override
    public BusinessProfile getBusinessProfileById(String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(BusinessProfile.class.getSimpleName()));
    }

    @Override
    public Map<String, String> createProfile(BusinessProfile profile, String product) {
        if (StringUtils.isNotBlank(product)) {
            profile.setSubscribedProducts(Collections.singleton(product));
        }
        long count = getCountByPanOrEin(profile.getPan(), profile.getEin());
        if (count != 0) {
            throw new AlreadyExistsException(BusinessProfile.class.getSimpleName());
        }
        String id = repository.save(profile).getId();
        return Collections.singletonMap("id", id);
    }

    private long getCountByPanOrEin(String pan, String ein) {
        if (StringUtils.isBlank(pan) && StringUtils.isBlank(ein)) {
            return 0;
        } else if (StringUtils.isNotBlank(pan) && StringUtils.isNotBlank(ein)) {
            return repository.countByPanOrEin(pan, ein);
        } else if (StringUtils.isNotBlank(pan)) {
            return repository.countByPan(pan);
        } else {
            return repository.countByEin(ein);
        }
    }

    //Approval api will not be called for calling product
    @Override
    public void updateProfile(BusinessProfile profile, String product) {
        BusinessProfile existingProfile = repository.getProductsByProfileId(profile.getId()).orElseThrow(() -> new NotFoundException(BusinessProfile.class.getSimpleName()));
        validateProfile(profile, existingProfile.getSubscribedProducts(), product);
        if (StringUtils.isNotBlank(product)) {
            existingProfile.getSubscribedProducts().add(product);
        }
        profile.setSubscribedProducts(existingProfile.getSubscribedProducts());
        repository.save(profile);
    }

    private void validateProfile(BusinessProfile profile, Set<String> products, String callingProduct) {
        products = products.stream().filter(p -> StringUtils.equals(p, callingProduct)).collect(Collectors.toSet());
        List<Future<ApprovalResponse>> futureResponses = getFutures(profile, products);
        validateFutureResponses(futureResponses);
    }

    private void validateFutureResponses(List<Future<ApprovalResponse>> futureResponses) {
        while (!futureResponses.stream().allMatch(Future::isDone)) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                log.error("Error while waiting for approval response: {}", e.getMessage());
                throw new RuntimeException(e);
            }
            for (Future<ApprovalResponse> future : futureResponses) {
                try {
                    validateResponse(future.get());
                } catch (ExecutionException | InterruptedException e) {
                    log.error("Error while getting approval response: {}", e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }

    }

    private void validateResponse(ApprovalResponse response) {
        if (response.getStatus() == ApprovalStatus.FAILED) {
            throw new ServiceUnavailableException("Product Validation service");
        } else if (response.getStatus() != ApprovalStatus.APPROVED) {
            throw new ValidationFailureException("Failed to validate profile. Errors : " + response.getErrors());
        }
    }

    private List<Future<ApprovalResponse>> getFutures(BusinessProfile profile, Set<String> products) {
        List<Future<ApprovalResponse>> futureResponses = new ArrayList<>();
        for (String product : products) {
            String url = getProductUrl(product);
            if (StringUtils.isBlank(url)) {
                throw new NotFoundException("Business profile validation url exposed by product : '" + product + "' ");
            }
            futureResponses.add(asyncService.getApproval(profile, url));
        }
        return futureResponses;
    }

    private String getProductUrl(String productName) {
        return env.getProperty("product-urls." + productName);
    }

    @Override
    public void deleteProfile(String id) {
        repository.deleteById(id);
    }
}
