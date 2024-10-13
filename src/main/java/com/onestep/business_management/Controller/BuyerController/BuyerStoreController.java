package com.onestep.business_management.Controller.BuyerController;

import com.onestep.business_management.DTO.API.ApiResponse;
import com.onestep.business_management.DTO.ProductDTO.ProductResponse;
import com.onestep.business_management.DTO.StoreDTO.StoreRequest;
import com.onestep.business_management.DTO.StoreDTO.StoreResponse;
import com.onestep.business_management.Service.StoreService.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/buyer/stores")
public class BuyerStoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        try {
            List<StoreResponse> response = storeService.getAllStores();
            ApiResponse<List<StoreResponse>> apiResponse = new ApiResponse<>(
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
}
