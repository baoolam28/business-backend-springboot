package com.onestep.business_management.DTO.OrderDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderOnlineDetailRequest {

    private Integer storeId;
    private int quantity;
    private double price;
    private String barcode;
}