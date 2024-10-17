package com.onestep.business_management.Controller.SellerController;

import com.onestep.business_management.DTO.API.ApiResponse;
import com.onestep.business_management.DTO.InventoryDTO.InventoryResponse;
import com.onestep.business_management.DTO.SupplierDTO.SupplierResponse;
import com.onestep.business_management.Service.InventoryService.InventoryService;
import com.onestep.business_management.Service.SupplierService.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/seller/inventories")
public class InventorySellerController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/{storeId}")
    public ResponseEntity<?> getAllSupplier(@PathVariable UUID storeId) {
        try {
            List<InventoryResponse> response = inventoryService.getInventoryByStore(storeId);
            ApiResponse<List<InventoryResponse>> apiResponse = new ApiResponse<>(
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
