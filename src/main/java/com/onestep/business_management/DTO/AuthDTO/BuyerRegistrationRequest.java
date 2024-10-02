package com.onestep.business_management.DTO.AuthDTO;

import com.onestep.business_management.Entity.Image;
import com.onestep.business_management.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class BuyerRegistrationRequest {
    private String username;
    private String password;
    private String phoneNumber;
    private String fullName;
    private Set<Role> role;
}
