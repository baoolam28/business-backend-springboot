package com.onestep.business_management.Controller.SellerController;

import com.onestep.business_management.DTO.API.ApiResponse;
import com.onestep.business_management.DTO.ProductDTO.ProductResponse;
import com.onestep.business_management.DTO.SupplierDTO.SupplierRequest;
import com.onestep.business_management.DTO.SupplierDTO.SupplierResponse;
import com.onestep.business_management.Service.SupplierService.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/seller/suppliers")
public class SupplierSellerController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping("/{storeId}")
    public ResponseEntity<?> getAllSupplier(@PathVariable UUID storeId) {
        try {
            List<SupplierResponse> response = supplierService.getAllByStore(storeId);
            ApiResponse<List<SupplierResponse>> apiResponse = new ApiResponse<>(
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


    @PostMapping()
    public ResponseEntity<?> createSupplier(@RequestBody SupplierRequest request) {
        try {
            SupplierResponse response = supplierService.create_supplier(request);
            ApiResponse<SupplierResponse> apiResponse = new ApiResponse<>(
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
