package com.onestep.business_management.DTO.AuthDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class GoogleSignInRequest {
    private String name;
    private String email;
    private String image;
    private String at_hash;

}
