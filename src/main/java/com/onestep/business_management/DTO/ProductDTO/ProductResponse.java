package com.onestep.business_management.DTO.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.onestep.business_management.Entity.Image;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Integer productId;
    private String barcode;
    private String productName;
    private List<Image> images = new ArrayList<>();
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
