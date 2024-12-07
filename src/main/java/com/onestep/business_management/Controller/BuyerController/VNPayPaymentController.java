package com.onestep.business_management.Controller.BuyerController;

import com.onestep.business_management.DTO.API.ApiResponse;
import com.onestep.business_management.DTO.PaymentDTO.VNPayRequest;
import com.onestep.business_management.DTO.ShipmentAddressDTO.ShipmentAddressRequest;
import com.onestep.business_management.DTO.ShipmentAddressDTO.ShipmentAddressRespone;
import com.onestep.business_management.Service.VNPayService.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/buyer/vnpay")
public class VNPayPaymentController {

    @Autowired
    private VNPayService vnPayService;

    @GetMapping("/create-url-payment")
    public ResponseEntity<?> createUrlPayment(@RequestBody VNPayRequest vnPayRequest, HttpServletRequest httpRq) {
        try {
            String urlPayment = vnPayService.getPaymentUrl(vnPayRequest, httpRq);
            ApiResponse<String> apiResponse = new ApiResponse<>(
                    HttpStatus.CREATED.value(),
                    "Shipping address saved successfully",
                    urlPayment,
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

    @PostMapping("/updatePayment")
    public String updatePayment(@RequestBody VNPayRequest vnPayRequest) {
        System.out.println("orderId: "+ vnPayRequest.getOrderId());
        return "call api success";
    }
}
