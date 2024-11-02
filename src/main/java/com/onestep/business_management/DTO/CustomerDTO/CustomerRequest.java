package com.onestep.business_management.DTO.CustomerDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    private UUID storeId;
    private String name;
    private String email;
    private String phone;
    private String address;
}
