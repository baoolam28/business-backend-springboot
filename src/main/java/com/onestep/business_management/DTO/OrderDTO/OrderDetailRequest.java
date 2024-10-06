package com.onestep.business_management.DTO.OrderDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailRequest {
    private UUID storeId;
    private int quantity;
    private double price;
    private String barcode;
}
