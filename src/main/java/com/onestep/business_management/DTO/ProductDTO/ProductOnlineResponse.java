package com.onestep.business_management.DTO.ProductDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOnlineResponse {
    private Integer productId;
    private String productName;
    private Double price;
    private String description;
    private String categoryName;
    private UUID storeId;
    private String storeName;
    private String district;
    private List<Variants> variants;
    private List<String> images;
}
