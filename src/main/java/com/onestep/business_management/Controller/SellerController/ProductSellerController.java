package com.onestep.business_management.Controller.SellerController;

import com.onestep.business_management.DTO.API.ApiResponse;
import com.onestep.business_management.DTO.ProductDTO.ProductRequest;
import com.onestep.business_management.DTO.ProductDTO.ProductResponse;
import com.onestep.business_management.Service.ProductService.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/seller/products")
public class ProductSellerController {

    @Autowired
    private ProductService productService;

    // Create product
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest prodRequest) {
            ProductResponse response = productService.createProduct(prodRequest);
            ApiResponse<ProductResponse> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),  // Status code 200
                    "Product created successfully",
                    response,
                    LocalDateTime.now()  // Current date
            );
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    // Get all products
    @GetMapping("/{storeId}")
    public ResponseEntity<?> getAllProducts(@PathVariable UUID storeId) {
        try {
            List<ProductResponse> response = productService.getAllByStore(storeId);
            ApiResponse<List<ProductResponse>> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Products retrieved successfully",
                    response,
                    LocalDateTime.now()
            );
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error retrieving products: " + e.getMessage());
            ApiResponse errorResponse = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
