package com.onestep.business_management.DTO.AuthDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String access_token;
    private UserInfo userInfo;
}
