package com.onestep.business_management.DTO;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreRequest {
    private String storeName;
    private String storeLocation;
    private String storeDescription;
    private UUID storeManagerId;
}
