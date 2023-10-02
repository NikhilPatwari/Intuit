package com.example.demo.dto.request.business_profile;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Address {

    @NotBlank(message = "Field : 'addressLine1' cannot be blank")
    @JsonProperty("addressLine1")
    private String addressLine1;

    @JsonProperty("addressLine2")
    private String addressLine2;

    @NotBlank(message = "Field : 'city' cannot be blank")
    @JsonProperty("city")
    private String city;

    @NotBlank(message = "Field : 'state' cannot be blank")
    @JsonProperty("state")
    private String state;

    @NotBlank(message = "Field : 'zip' cannot be blank")
    @JsonProperty("zip")
    private String zip;

    @NotBlank(message = "Field : 'country' cannot be blank")
    @JsonProperty("country")
    private String country;
}

