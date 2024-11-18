package com.onestep.business_management.DTO.AuthDTO;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBuyerInfoRequest {
    private String fullName;
    private String email;
    private String phoneNumber;
    private List<MultipartFile> imageName;
}
