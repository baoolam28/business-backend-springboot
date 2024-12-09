package com.onestep.business_management.DTO.StoreDTO;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreIsActiveRequest {
    private UUID storeId ; 
    private boolean active;
}
