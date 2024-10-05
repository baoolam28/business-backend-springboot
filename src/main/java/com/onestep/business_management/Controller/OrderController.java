package com.onestep.business_management.Controller;

import com.onestep.business_management.DTO.OrderDTO.OrderReportResponse;
import com.onestep.business_management.DTO.OrderDTO.OrderRequest;
import com.onestep.business_management.DTO.OrderDTO.OrderResponse;
import com.onestep.business_management.Service.OrderService.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrder() {
        try {
            List<OrderResponse> response = orderService.getAllOrders();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @PostMapping
    // public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest
    // orderRequest) {
    // try {
    // OrderResponse response = orderService.createOrder(orderRequest);
    // return new ResponseEntity<>(response, HttpStatus.CREATED);
    // } catch (Exception e) {
    // return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable UUID orderId) {
        try {
            OrderResponse response = orderService.getOrderById(orderId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{orderId}/payment")
    public ResponseEntity<OrderResponse> updateOrderPayment(@PathVariable UUID orderId,
            @RequestParam String paymentMethod,
            @RequestParam boolean paymentStatus) {
        try {
            OrderResponse response = orderService.updateOrderPayment(orderId, paymentMethod, paymentStatus);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reports")
    public ResponseEntity<OrderReportResponse> getOrderReports(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            // Set default values if parameters are not provided
            if (startDate == null) {
                startDate = LocalDate.now().minusMonths(1); // Default to one month ago
            }
            if (endDate == null) {
                endDate = LocalDate.now(); // Default to today
            }

            // Ensure endDate is not before startDate
            if (endDate.isBefore(startDate)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            // Call the service with the provided or default dates
            OrderReportResponse response = orderService.getOrderReports(startDate, endDate);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
