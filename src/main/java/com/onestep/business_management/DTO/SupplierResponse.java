package com.onestep.business_management.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SupplierResponse {
    private String supplierId;
    private String supplierName;
    private String email;
    private String phone;
    private String fax;
    private String address;
}
