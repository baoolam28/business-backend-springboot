package com.onestep.business_management.DTO.OrderDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderOnlineDetailRequest {

    private UUID storeId;
    private Integer productDetailId;
    private int quantity;
}