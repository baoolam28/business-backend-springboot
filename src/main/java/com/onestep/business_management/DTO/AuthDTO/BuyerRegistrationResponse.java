package com.onestep.business_management.DTO.AuthDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
public class BuyerRegistrationResponse {
    private UUID userId;
    private String username;
    private String phoneNumber;
    private String fullName;
    private Set<String> roles;
}
