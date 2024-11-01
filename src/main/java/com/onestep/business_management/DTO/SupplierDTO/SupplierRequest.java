package com.onestep.business_management.DTO.SupplierDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class SupplierRequest {
    private UUID storeId;
    private String supplierName;
    private String email;
    private String phone;
    private String fax;
    private String address;
}
