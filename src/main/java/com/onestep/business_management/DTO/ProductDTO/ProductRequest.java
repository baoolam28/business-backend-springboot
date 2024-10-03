package com.onestep.business_management.DTO.ProductDTO;

import java.util.UUID;
import java.util.Date;

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
    private Double price;
    private Integer supplierId;
    private Integer originId;
    private UUID createdBy;
    private UUID storeId;
    private Date createdDate;
}
