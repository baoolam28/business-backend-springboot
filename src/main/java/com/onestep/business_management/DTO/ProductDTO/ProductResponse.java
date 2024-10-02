package com.onestep.business_management.DTO.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private String barcode;
    private UUID userId;
    private String fullName;
    private String productName;
    private Integer categoryId;
    private String categoryName;
    private String abbreviations;
    private String unit;
    private Float price;
    private Integer supplierId;
    private String supplierName;
    private Integer originId;
    private String originName;
    private Date createdDate;
    private boolean disabled;
    
}
