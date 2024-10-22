package com.onestep.business_management.DTO.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Variants {
    private Integer productDetailId;
    private Double price;
    private int quantityInStock;
    private String sku;
    private String image;
    private Map<String,String> attributes;


}
