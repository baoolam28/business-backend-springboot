package com.onestep.business_management.DTO;

import com.onestep.business_management.Entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {
    private int orderDetailId;
    private int quantity;
    private double price;
    private String productName;
    private String barcode;
}
