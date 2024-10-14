package com.onestep.business_management.DTO.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOnlineRequest {
    private String productName;
    private String description;
    private Integer categoryId;
    private Double price;
    private UUID storeId;
    private List<MultipartFile> images;
    private List<ProductDetailRequest> productDetail;
}
