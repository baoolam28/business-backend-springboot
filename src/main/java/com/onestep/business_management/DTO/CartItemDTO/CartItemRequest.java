package com.onestep.business_management.DTO.CartItemDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequest {
    private UUID userId;
    private Integer productDetailId;
    private Integer quantity;
}
