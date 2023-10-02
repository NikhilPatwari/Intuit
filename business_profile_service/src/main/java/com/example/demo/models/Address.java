package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Address {

    @NotBlank(message = "Field : 'addressLine1' cannot be blank", groups = {CreateProfileGrp.class, UpdateProfileGrp.class})
    @JsonProperty("addressLine1")
    private String addressLine1;

    @JsonProperty("addressLine2")
    private String addressLine2;

    @NotBlank(message = "Field : 'city' cannot be blank", groups = {CreateProfileGrp.class, UpdateProfileGrp.class})
    @JsonProperty("city")
    private String city;

    @NotBlank(message = "Field : 'state' cannot be blank", groups = {CreateProfileGrp.class, UpdateProfileGrp.class})
    @JsonProperty("state")
    private String state;

    @NotBlank(message = "Field : 'zip' cannot be blank", groups = {CreateProfileGrp.class, UpdateProfileGrp.class})
    @JsonProperty("zip")
    private String zip;

    @NotBlank(message = "Field : 'country' cannot be blank", groups = {CreateProfileGrp.class, UpdateProfileGrp.class})
    @JsonProperty("country")
    private String country;
}
