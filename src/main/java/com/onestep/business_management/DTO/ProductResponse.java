package com.onestep.business_management.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private String barcode;
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
    private Integer createdBy;
    private boolean disabled;
}
