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
    private String storeName;
    private String storeLocation;
    private String storeDescription;
}
