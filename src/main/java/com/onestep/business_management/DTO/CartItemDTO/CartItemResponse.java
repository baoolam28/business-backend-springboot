package com.onestep.business_management.DTO.CartItemDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
    private Integer productDetailId;
    private UUID storeId;
    private String productName;
    private Integer quantity;
    private Double price; 
    private Double totalPrice;
    private String image;
    private Map<String,String> attributes;
}
