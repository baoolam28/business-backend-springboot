package com.onestep.business_management.Controller.AdminController;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.onestep.business_management.DTO.API.ApiResponse;
import com.onestep.business_management.DTO.StoreDTO.StoreIsActiveRequest;
import com.onestep.business_management.DTO.StoreDTO.StoreResponse;
import com.onestep.business_management.Service.CategoryService.CategoryService;
import com.onestep.business_management.Service.StoreService.StoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    StoreService storeService;

    @GetMapping("/stores")
    public ResponseEntity<?> getAllStores() {
        try {
            List<StoreResponse> response = storeService.getAllStores();
            ApiResponse<List<StoreResponse>> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Stores retrieved successfully",
                    response,
                    LocalDateTime.now());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error retrieving stores: " + e.getMessage());
            ApiResponse<Object> errorResponse = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/store/status")
    public ResponseEntity<?> updateStoreStatus(@RequestBody StoreIsActiveRequest request) {
        try {
            StoreResponse updatedStore = storeService.updateStoreStatus(request);
            ApiResponse<StoreResponse> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Store status updated successfully",
                    updatedStore,
                    LocalDateTime.now());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<Object> errorResponse = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
