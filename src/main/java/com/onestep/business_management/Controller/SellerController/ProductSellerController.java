package com.onestep.business_management.Controller.SellerController;

import com.onestep.business_management.DTO.API.ApiResponse;
import com.onestep.business_management.DTO.ProductDTO.*;
import com.onestep.business_management.Service.ProductService.ProductService;
import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/seller/products")
public class ProductSellerController {

    @Autowired
    private ProductService productService;


    @PostMapping(value = "/online", consumes = {"multipart/form-data"})
    public ResponseEntity<?> createProductOnline(@ModelAttribute ProductOnlineRequest prodRequest) {
        System.out.println("prodrequest: "+prodRequest);
        ProductOnlineResponse response = productService.createProductOnline(prodRequest);
        ApiResponse<ProductOnlineResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),  // Status code 200
                "Product Online created successfully",
                response,
                LocalDateTime.now()  // Current date
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

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


    // Get all products
    @GetMapping("/online/{storeId}")
    public ResponseEntity<?> getAllProductsOnline(@PathVariable UUID storeId) {
        try {
            List<ProductResponse> response = productService.getAllOnlineByStore(storeId);
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

    @GetMapping("/offline/{storeId}")
    public ResponseEntity<?> getAllProductsOffline(@PathVariable UUID storeId) {
        try {
            List<ProductResponse> response = productService.getAllOfflineByStore(storeId);
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

    @PostMapping(value = "/offline", consumes = {"multipart/form-data"})
    public ResponseEntity<?> createProduct(@ModelAttribute ProductRequest productRequest) {
        try {
            ProductResponse response = productService.createProduct(productRequest);
            ApiResponse<ProductResponse> apiResponse = new ApiResponse<>(
                    HttpStatus.CREATED.value(),
                    "Product created successfully",
                    response,
                    LocalDateTime.now());
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{barcode}")
    public ResponseEntity<?> updateProduct(@RequestBody ProductRequest productRequest,@PathVariable("barcode") String barcode ){
        try {
            ProductResponse response = productService.updateProduct(productRequest);
            if (response != null) {
                ApiResponse<ProductResponse> apiResponse = new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Product updated successfully",
                        response,
                        LocalDateTime.now());
                return new ResponseEntity<>(apiResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Product not found", null, LocalDateTime.now()),
                        HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @DeleteMapping("/delete/{barcode}")
    public ResponseEntity<?> deleteProduct(@PathVariable("barcode") String barcode) {
        try {
            ProductResponse response = productService.deleteProduct(barcode);
            if (response != null) {
                ApiResponse<ProductResponse> apiResponse = new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Product deleted successfully",
                        response,
                        LocalDateTime.now());
                return new ResponseEntity<>(apiResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Product not found", null, LocalDateTime.now()),
                        HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
