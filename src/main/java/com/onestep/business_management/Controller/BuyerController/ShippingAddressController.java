package com.onestep.business_management.Controller.BuyerController;

import com.onestep.business_management.DTO.API.ApiResponse;
import com.onestep.business_management.DTO.ShipmentAddressDTO.ShipmentAddressRequest;
import com.onestep.business_management.DTO.ShipmentAddressDTO.ShipmentAddressRespone;
import com.onestep.business_management.Service.ShipmentAddressService.ShipmentAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/buyer/shipping-addresses") // Đường dẫn chung cho tất cả các endpoint
public class ShippingAddressController {

    @Autowired
    private ShipmentAddressService shipmentAddressService;

    // Tạo hoặc cập nhật địa chỉ giao hàng
    @PostMapping()
    public ResponseEntity<?> createOrUpdateShippingAddress(@RequestBody ShipmentAddressRequest shippingAddressRequest) {
        try {
            System.out.println(shippingAddressRequest.toString());
            ShipmentAddressRespone response = shipmentAddressService.saveShippingAddress(shippingAddressRequest);
            ApiResponse<ShipmentAddressRespone> apiResponse = new ApiResponse<>(
                    HttpStatus.CREATED.value(),
                    "Shipping address saved successfully",
                    response,
                    LocalDateTime.now());
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Error saving shipping address: " + e.getMessage());
            ApiResponse<?> errorResponse = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Lấy địa chỉ giao hàng theo ID người dùng
    @GetMapping("/{userId}")
    public ResponseEntity<?> getShippingAddressesByUserId(
            @PathVariable UUID userId) {
        try {
            List<ShipmentAddressRespone> addresses = shipmentAddressService.getShippingAddressById(userId);
            ApiResponse<List<ShipmentAddressRespone>> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Shipping addresses retrieved successfully",
                    addresses,
                    LocalDateTime.now());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error retrieving shipping addresses: " + e.getMessage());
            ApiResponse<?> errorResponse = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Xóa địa chỉ giao hàng theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShippingAddress(@PathVariable Integer id) {
        shipmentAddressService.deleteShippingAddressById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
