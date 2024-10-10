package com.onestep.business_management.Controller.SellerController;

import java.time.LocalDateTime;
import java.util.List;

import com.onestep.business_management.DTO.API.ApiResponse;
import com.onestep.business_management.DTO.ProductDTO.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onestep.business_management.DTO.CategoryDTO.CategoryResponse;
import com.onestep.business_management.Service.CategoryService.CategoryService;

@RestController
@RequestMapping("/api/seller/categories")
public class CategorySellerController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        List<CategoryResponse> response = categoryService.getAllCategories();
        ApiResponse<List<CategoryResponse>> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),  // Status code 200
                "Category retrieved successfully",
                response,
                LocalDateTime.now()  // Current date
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    // Get Category by ID (Admin only)
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Integer id) {
        try {
            CategoryResponse response = categoryService.getCategoryById(id);
            if (response != null) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
