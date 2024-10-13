package com.onestep.business_management.DTO.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryReponse {
    private Integer productId;
    private Integer categoryId;
    private String productName;
    private Double price;
    private String storeName;
    private String pickupAddress;
    private String categoryName;
    private Double rating;
    private Integer totalReviews;
}
