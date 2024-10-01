package com.onestep.business_management.DTO.OrderDTO;

import com.onestep.business_management.DTO.ProductDTO.ProductResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderOnlineDetailResponse {
    private int orderDetailId;
    private int quantity;
    private double price;
    private ProductResponse product;
}