package com.onestep.business_management.DTO.StoreDTO;

import java.time.LocalDateTime;
import java.util.UUID;

import com.onestep.business_management.Entity.Image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreResponse {
    private UUID storeId;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isAcctive;
}
