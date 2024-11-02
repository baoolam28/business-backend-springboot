package com.onestep.business_management.DTO.ProductDTO;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.onestep.business_management.Entity.Image;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String barcode;
    private List<MultipartFile> images ; 
    private String productName;
    private Integer categoryId;
    private String abbreviations;
    private String unit;
    private Double price;
    private Integer supplierId;
    private Integer originId;
    private UUID createdBy;
    private UUID storeId;
    
}
