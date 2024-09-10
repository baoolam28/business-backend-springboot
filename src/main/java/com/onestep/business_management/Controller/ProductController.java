package com.onestep.business_management.Controller;

import com.onestep.business_management.DTO.ProductRequest;
import com.onestep.business_management.DTO.ProductResponse;
import com.onestep.business_management.Service.ProductService.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Endpoint to create a new product
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        try {
            ProductResponse response = productService.createProduct(productRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle exceptions
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to get all products
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        try {
            List<ProductResponse> response = productService.getAll();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to get a product by barcode
    @GetMapping("/{barcode}")
    public ResponseEntity<ProductResponse> getProductByBarcode(@PathVariable("barcode") String barcode) {
        try {
            ProductResponse response = productService.getByBarcode(barcode);
            if (response != null) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Handle exceptions
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to search products by keyword
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

    // Endpoint to find products by category ID
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> findByCategory(@PathVariable("categoryId") int categoryId) {
        try {
            List<ProductResponse> response = productService.findByCategory(categoryId);
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

    // Endpoint to find products by supplier ID
    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<ProductResponse>> findBySupplier(@PathVariable("supplierId") int supplierId) {
        try {
            List<ProductResponse> response = productService.findBySupplier(supplierId);
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

    // Endpoint to find products by origin ID
    @GetMapping("/origin/{originId}")
    public ResponseEntity<List<ProductResponse>> findByOrigin(@PathVariable("originId") int originId) {
        try {
            List<ProductResponse> response = productService.findByOrigin(originId);
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

    // Endpoint to update a product
    @PutMapping("/{barcode}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("barcode") String barcode, @RequestBody ProductRequest productRequest) {
        try {
            ProductResponse response = productService.updateProduct(productRequest);
            if (response != null) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Handle exceptions
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to delete a product
    @DeleteMapping("/{barcode}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("barcode") String barcode) {
        try {
            ProductResponse response = productService.deleteProduct(barcode);
            if (response != null) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Handle exceptions
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
