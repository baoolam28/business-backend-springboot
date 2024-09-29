package com.onestep.business_management.DTO;

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