package com.onestep.business_management.Controller.BuyerController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onestep.business_management.DTO.API.ApiResponse;
import com.onestep.business_management.DTO.OrderDTO.OrderOnlineDetailRequest;
import com.onestep.business_management.DTO.OrderDTO.OrderOnlineRequest;
import com.onestep.business_management.DTO.OrderDTO.OrderOnlineResponse;
import com.onestep.business_management.DTO.ShippingDTO.ShipmentResponse;
import com.onestep.business_management.DTO.StoreDTO.StoreResponse;
import com.onestep.business_management.Service.OrderOnlineService.OrderOnlineService;
import com.onestep.business_management.Service.ShipmentService.ShipmentService;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/buyer/purchase")
public class BuyerOrderOnlineDetailController {
    
    @Autowired
    private ShipmentService shipmentService;

    @GetMapping("/{shipmentId}")
    public ResponseEntity<?> getOrderStatus(@PathVariable("shipmentId") Integer shipmentId) {
       try {
            ShipmentResponse response = shipmentService.getShipmentById(shipmentId);
            ApiResponse<ShipmentResponse> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Store retrieved successfully",
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


    @GetMapping("/orderOnline/{userId}")
    public ResponseEntity<?> getAllOrderStatus(@PathVariable("userId") UUID userId) {
       try {
            List<ShipmentResponse> response = shipmentService.getOrdersOnlineByUser(userId);
            ApiResponse<List<ShipmentResponse>> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Store retrieved successfully",
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
