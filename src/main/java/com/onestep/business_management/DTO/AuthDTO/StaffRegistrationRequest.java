package com.onestep.business_management.DTO.AuthDTO;

import java.util.Set;

import com.onestep.business_management.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

@Data
@AllArgsConstructor
public class StaffRegistrationRequest {
    
    private String username;
    private String password;
    private String phoneNumber;
    private String fullName;
    private String email;
    private UUID storeId;
}
