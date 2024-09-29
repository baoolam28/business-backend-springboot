package com.onestep.business_management.DTO;

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
    private String storeName;
    private String storeLocation;
    private String storeDescription;
    private UUID storeManagerId;
    private String storeManagerName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
