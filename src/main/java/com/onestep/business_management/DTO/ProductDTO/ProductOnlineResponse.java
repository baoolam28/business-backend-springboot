package com.onestep.business_management.DTO.ProductDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOnlineResponse {
    private Integer productId;
    private String productName;
    private Double price;
    private String description;
    private String categoryName;
    private String storeName;
    private List<Variants> variants;
    private List<String> images;
}
