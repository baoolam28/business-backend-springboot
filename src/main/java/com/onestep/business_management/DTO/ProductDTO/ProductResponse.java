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
    private Integer productId;
    private String barcode;
    private String productName;
    private Integer categoryId;
    private String categoryName;
    private String abbreviations;
    private String unit;
    private Double price;
    private Integer supplierId;
    private String supplierName;
    private Integer originId;
    private String originName;
    private UUID createdBy;
    private Date createdDate;
    private boolean disabled;
}
