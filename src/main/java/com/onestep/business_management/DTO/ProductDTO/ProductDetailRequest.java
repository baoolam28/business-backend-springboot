package com.onestep.business_management.DTO.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailRequest {
    private Double price;
    private String sku;
    private int quantityInStock;
    private MultipartFile image;
    private Double height;
    private Double length;
    private Double width;
    private Double weight;
    private Map<String,String> attributes;
}
