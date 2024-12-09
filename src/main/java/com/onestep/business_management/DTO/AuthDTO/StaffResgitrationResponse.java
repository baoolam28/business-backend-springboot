package com.onestep.business_management.DTO.AuthDTO;

import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StaffResgitrationResponse {
 
    private UUID userId;
    private String username;
    private String phoneNumber;
    private String fullName;
    private String email;
    private Set<String> roles;
}
