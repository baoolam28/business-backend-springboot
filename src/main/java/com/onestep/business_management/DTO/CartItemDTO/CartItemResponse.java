package com.onestep.business_management.DTO.CartItemDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
    private Integer productId;
    private String productName;
    private String barcode;
    private Integer quantity;
    private Double price; 
    private Double totalPrice;
}
