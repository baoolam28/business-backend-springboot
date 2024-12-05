package com.onestep.business_management.DTO.ProductDTO;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailResponse {
    private String productName;
    private String image;
    private Double price;
    private Map<String, String> attributes;
}
