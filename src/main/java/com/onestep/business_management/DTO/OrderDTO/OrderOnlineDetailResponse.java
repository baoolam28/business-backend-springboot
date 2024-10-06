package com.onestep.business_management.DTO.OrderDTO;

import com.onestep.business_management.DTO.ProductDTO.ProductResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderOnlineDetailResponse {
    private UUID orderDetailId;
    private Integer productId;
    private String productName;
    private String barcode;
    private int quantity;
    private double price;
}