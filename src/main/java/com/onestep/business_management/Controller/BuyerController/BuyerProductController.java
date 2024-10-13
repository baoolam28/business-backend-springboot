package com.onestep.business_management.Controller.BuyerController;

import java.time.LocalDateTime;
import java.util.List;

import com.onestep.business_management.DTO.API.ApiResponse;
import com.onestep.business_management.DTO.ProductDTO.ProductCategoryReponse;
import com.onestep.business_management.DTO.OrderDTO.OrderOnlineRequest;
import com.onestep.business_management.DTO.OrderDTO.OrderOnlineResponse;

import com.onestep.business_management.DTO.ProductDTO.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.onestep.business_management.DTO.ProductDTO.ProductResponse;
import com.onestep.business_management.DTO.RiviewDTO.ReviewResponse;
import com.onestep.business_management.Exeption.ResourceNotFoundException;
import com.onestep.business_management.Service.ProductService.ProductService;
import com.onestep.business_management.Service.ReviewSevice.ReviewService;

@RestController
@RequestMapping("/api/buyer/products")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        try {
            List<ProductResponse> response = productService.getAll();
            ApiResponse<List<ProductResponse>> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Products retrieved successfully",
                    response,
                    LocalDateTime.now());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error retrieving products: " + e.getMessage());
            ApiResponse errorResponse = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable("id") Integer productId) {
        try {
            ProductResponse response = productService.findProductById(productId);
            if (response != null) {
                return new ResponseEntity<>(
                        new ApiResponse<>(HttpStatus.OK.value(), "Product found", response, LocalDateTime.now()),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Product not found", null, LocalDateTime.now()),
                        HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null,
                            LocalDateTime.now()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchByKeyword(@RequestParam("keyword") String keyword) {
        try {
            List<ProductResponse> response = productService.searchByKeyword(keyword);
            if (response != null && !response.isEmpty()) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Handle exceptions
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ProductCategoryReponse>>> findByCategory(
            @PathVariable("categoryId") int categoryId) {
        try {
            List<ProductCategoryReponse> response = productService.findByCategoryId(categoryId);
            // Nếu không có ngoại lệ, trả về danh sách sản phẩm
            ApiResponse<List<ProductCategoryReponse>> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Products retrieved successfully",
                    response,
                    LocalDateTime.now());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            // Trả về 404 nếu không tìm thấy sản phẩm
            ApiResponse<List<ProductCategoryReponse>> apiResponse = new ApiResponse<>(
                    HttpStatus.NOT_FOUND.value(),
                    e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Xử lý ngoại lệ khác
            ApiResponse<List<ProductCategoryReponse>> apiResponse = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An error occurred: " + e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
