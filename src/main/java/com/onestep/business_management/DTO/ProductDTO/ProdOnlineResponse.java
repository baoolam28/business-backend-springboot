package com.onestep.business_management.DTO.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdOnlineResponse {
    private Integer productId;
    private Integer categoryId;
    private String categoryName;
    private String productName;
    private Double price;
    private String storeName;
    private String pickupAddress;
    private Double rating;
    private Integer totalReviews;
    private List<String> images;
    private String description;
    private UUID storeId;
    private String district;
}
