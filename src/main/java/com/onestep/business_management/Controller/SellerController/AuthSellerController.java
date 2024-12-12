package com.onestep.business_management.Controller.SellerController;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.onestep.business_management.DTO.API.ApiResponse;
import com.onestep.business_management.DTO.AuthDTO.BuyerRegistrationRequest;
import com.onestep.business_management.DTO.AuthDTO.BuyerRegistrationResponse;
import com.onestep.business_management.DTO.AuthDTO.StaffRegistrationRequest;
import com.onestep.business_management.DTO.AuthDTO.StaffResgitrationResponse;
import com.onestep.business_management.DTO.InventoryDTO.InventoryResponse;
import com.onestep.business_management.DTO.OrderDTO.OrderResponse;
import com.onestep.business_management.Entity.User;
import com.onestep.business_management.Exeption.ResourceNotFoundException;
import com.onestep.business_management.Service.AuthService.AuthenticationService;
import java.util.UUID;

@RestController
@RequestMapping("/api/seller/auth")
public class AuthSellerController {
    
    @Autowired
    private AuthenticationService authService;

    @GetMapping("/{storeId}")
    public ResponseEntity<?> getAllStaffByStoreId(@PathVariable UUID storeId) {
        try {
            List<StaffResgitrationResponse> staffList = authService.getAllStaffByStoreId(storeId);
            return ResponseEntity.ok(new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Staff retrieved successfully",
                    staffList,
                    LocalDateTime.now()));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error retrieving staff: " + e.getMessage(),
                    null,
                    LocalDateTime.now()));
        }
    }

    @PostMapping
    public ResponseEntity<?> staffRegister(@RequestBody StaffRegistrationRequest staffRegistrationRequest) {
        try {
            StaffResgitrationResponse response = authService.staffRegister(staffRegistrationRequest);
            ApiResponse<?> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Staff registered successfully",
                    response,
                    LocalDateTime.now());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error registering staff: " + e.getMessage());
            ApiResponse errorResponse = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to register staff",
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteStaff(@PathVariable String userId) {
        try {
            // Kiểm tra nếu userId là UUID hợp lệ
            UUID uuid = UUID.fromString(userId); // Sẽ gây lỗi nếu không phải UUID hợp lệ
            authService.deleteStaff(uuid);

            ApiResponse<?> apiResponse = new ApiResponse<>(
                    HttpStatus.NO_CONTENT.value(),
                    "Staff deleted successfully",
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            // Trả về lỗi 400 nếu userId không hợp lệ
            ApiResponse<Void> errorResponse = new ApiResponse<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid UUID format",
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println("Error deleting staff: " + e.getMessage());
            ApiResponse<Void> errorResponse = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to delete staff",
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
