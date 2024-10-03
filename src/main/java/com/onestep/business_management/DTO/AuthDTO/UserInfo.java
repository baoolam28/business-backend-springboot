package com.onestep.business_management.DTO.AuthDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserInfo {
    private UUID userId;
    private String username;
    private String fullName;
    private String phoneNumber;
    private String role;
    private boolean isActive;

    public UserInfo() {

    }
}
