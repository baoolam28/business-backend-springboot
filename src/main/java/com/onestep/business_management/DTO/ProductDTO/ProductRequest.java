package com.onestep.business_management.DTO.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String barcode;
    private String productName;
    private Integer categoryId;
    private String abbreviations;
    private String unit;
    private Float price;
    private Integer supplierId;
    private Integer originId;
    private Integer createdBy;
    private boolean disabled;
    private Integer reviewId;   
}
