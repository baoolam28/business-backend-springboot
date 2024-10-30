package com.onestep.business_management.Controller.BuyerController;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onestep.business_management.DTO.API.ApiResponse;
import com.onestep.business_management.DTO.AuthDTO.BuyerRegistrationRequest;
import com.onestep.business_management.DTO.AuthDTO.BuyerRegistrationResponse;
import com.onestep.business_management.Service.AuthService.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/buyer")
public class BuyerRegisterController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerNewBuyer (@RequestBody BuyerRegistrationRequest request) {
       try {
        BuyerRegistrationResponse response = authenticationService.buyer_register(request);
        ApiResponse<BuyerRegistrationResponse> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Register User retrieved successfully",
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
