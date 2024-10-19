package com.onestep.business_management.DTO.OrderDTO;

import com.onestep.business_management.DTO.ProductDTO.ProductResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderOnlineDetailResponse {
    private UUID orderDetailId;
    private Integer productDetailId;
    private String productName;
    private int quantity;
    private double price;
    private Double totalPrice;
    private String image;
    private Map<String, String> attributes;
}