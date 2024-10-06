package com.onestep.business_management.DTO.CartDTO;

import java.util.List;
import java.util.UUID;
import com.onestep.business_management.DTO.CartItemDTO.CartItemResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private Integer cartId;
    private UUID userId;
    private List<CartItemResponse> cartItems;
}
