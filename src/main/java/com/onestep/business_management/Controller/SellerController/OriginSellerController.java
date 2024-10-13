package com.onestep.business_management.Controller.SellerController;

import com.onestep.business_management.DTO.API.ApiResponse;
import com.onestep.business_management.DTO.CategoryDTO.CategoryResponse;
import com.onestep.business_management.DTO.OriginDTO.OriginResponse;
import com.onestep.business_management.Service.CategoryService.CategoryService;
import com.onestep.business_management.Service.OriginService.OriginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/seller/origins")
public class OriginSellerController {
    @Autowired
    private OriginService originService;

    @GetMapping
    public ResponseEntity<?> getAllOrigins() {
        List<OriginResponse> response = originService.getAllOrigins();
        ApiResponse<List<OriginResponse>> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),  // Status code 200
                "Category retrieved successfully",
                response,
                LocalDateTime.now()  // Current date
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
