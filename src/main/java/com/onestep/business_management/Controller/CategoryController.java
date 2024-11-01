package com.onestep.business_management.Controller;

import com.onestep.business_management.DTO.API.ApiResponse;
import com.onestep.business_management.DTO.CategoryDTO.CategoryRequest;
import com.onestep.business_management.DTO.CategoryDTO.CategoryResponse;
import com.onestep.business_management.DTO.ProductDTO.ProductResponse;
import com.onestep.business_management.Service.CategoryService.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/buyer/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Get All Categories
    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        try {
            List<CategoryResponse> response = categoryService.getAllCategories();
            ApiResponse<List<CategoryResponse>> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(), "Get all successfully", response, LocalDateTime.now());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get Category by ID
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

    // Create or Update Category
    @PostMapping
    public ResponseEntity<CategoryResponse> createOrUpdateCategory(@RequestBody CategoryRequest categoryRequest) {
        try {
            System.out.println("Received CategoryRequest: " + categoryRequest);
            CategoryResponse response = categoryService.saveCategory(categoryRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update Category by ID
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Integer id,
            @RequestBody CategoryRequest categoryRequest) {
        try {
            CategoryResponse existingCategory = categoryService.getCategoryById(id);
            if (existingCategory != null) {
                CategoryResponse updatedCategory = categoryService.saveCategory(categoryRequest);
                return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete Category by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        try {   
            categoryService.deleteCategoryById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
