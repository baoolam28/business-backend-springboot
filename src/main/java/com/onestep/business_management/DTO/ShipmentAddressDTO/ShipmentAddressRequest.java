package com.onestep.business_management.DTO.ShipmentAddressDTO;

import java.util.UUID;

import com.onestep.business_management.Entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentAddressRequest {
    private UUID userId;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String province;
    private String district;
    private String wardCode;
    private boolean disabled;
}
