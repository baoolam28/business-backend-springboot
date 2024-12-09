package com.onestep.business_management.DTO.AuthDTO;

import java.util.Set;

import com.onestep.business_management.Entity.Role;
import com.onestep.business_management.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminRegisterRequest {
    private String username;
    private String password;
    private String phoneNumber;
    private String fullName;
    private String email;
    private Set<Role> role;
}
