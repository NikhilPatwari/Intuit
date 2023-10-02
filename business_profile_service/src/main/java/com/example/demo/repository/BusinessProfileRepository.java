package com.example.demo.repository;

import com.example.demo.models.BusinessProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface BusinessProfileRepository extends MongoRepository<BusinessProfile, String> {

    long countByPan(String pan);

    long countByEin(String ein);

    long countByPanOrEin(String pan, String ein);

    @Query(value = "{ _id : ?0 }", fields = "{ subscribedProducts : 1, _id: 0}")
    Optional<BusinessProfile> getProductsByProfileId(String id);
}
