package com.onestep.business_management.DTO.CartItemDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequest {
    private Integer productId;
    private Integer quantity;
    private Double price; 
}
