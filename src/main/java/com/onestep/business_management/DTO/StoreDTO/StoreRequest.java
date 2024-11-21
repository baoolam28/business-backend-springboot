package com.onestep.business_management.DTO.StoreDTO;

import java.util.UUID;

import com.onestep.business_management.Entity.Image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreRequest {
    private String storeName;
    private Image storeAvatar;
    private String storeLocation;
    private String storeDescription;
    private String storeEmail;
    private String storeBankAccount;
    private String pickupAddress;
    private String storeTaxCode;
    private UUID storeManager;
    private String managerName;
    private String wardCode;
}
