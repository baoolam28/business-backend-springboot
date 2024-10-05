package com.onestep.business_management.DTO.ShipmentAddressDTO;

import java.util.UUID;

import com.onestep.business_management.Entity.User;

import lombok.Data;

@Data
public class ShipmentAddressRequest {
    private User userId;
    private String fullName;
    private String phoneNumber;
    private String addressLine;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private boolean disabled;
}
