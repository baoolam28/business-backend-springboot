package com.onestep.business_management.DTO.AuthDTO;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBuyerInfoRespont {
    private UUID userId;
    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String imageName;
}
