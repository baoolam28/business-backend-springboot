package com.onestep.business_management.DTO.CartDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.onestep.business_management.DTO.CartItemDTO.CartItemRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {
    private UUID userId;
    private Integer productDetailId;
    private Integer quantity;
}
