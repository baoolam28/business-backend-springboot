package com.onestep.business_management.DTO.CartItemDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
    private Integer productDetailId;
    private String productName;
    private Integer quantity;
    private Double price; 
    private Double totalPrice;
    private String image;
    private Map<String,String> attributes;
}
