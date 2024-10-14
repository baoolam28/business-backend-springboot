package com.onestep.business_management.DTO.OrderDTO;

import com.onestep.business_management.DTO.ProductDTO.ProductResponse;
import com.onestep.business_management.Entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {
    private UUID orderDetailId;
    private String name;
    private String barcode;
    private int quantity;
    private double price;

}
