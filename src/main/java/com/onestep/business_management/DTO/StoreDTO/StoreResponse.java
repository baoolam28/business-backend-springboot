package com.onestep.business_management.DTO.StoreDTO;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreResponse {
    private UUID storeId;
    private UUID storeManagerId;
    private String storeImage;
    private String storeName;
    private String storeLocation;
    private String storeDescription;
    private String storeManagerName;
    private String storeManagerPhone;
    private String storeEmail;
    private String storeTaxCode;
    private String storeBankAccount;
    private String district;
    private String province;
    private String wardCode;
    private String pickupAddress;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
