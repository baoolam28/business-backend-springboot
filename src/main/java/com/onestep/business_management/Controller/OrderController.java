package com.onestep.business_management.Controller;

import com.onestep.business_management.DTO.OrderDTO.OrderReportResponse;
import com.onestep.business_management.DTO.OrderDTO.OrderRequest;
import com.onestep.business_management.DTO.OrderDTO.OrderResponse;
import com.onestep.business_management.Exeption.ResourceNotFoundException;
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

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        System.out.println(orderRequest.toString());
        try {
            OrderResponse response = orderService.createOrder(orderRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable String orderId) {
        UUID uuid = UUID.fromString(orderId);
        try {
            OrderResponse response = orderService.getOrderById(uuid);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{orderId}/payment")
    public ResponseEntity<OrderResponse> updateOrderPayment(
            @PathVariable String orderId,
            @RequestParam String paymentMethod,
            @RequestParam boolean paymentStatus) {

        UUID uuid = UUID.fromString(orderId);

        try {
            OrderResponse response = orderService.updateOrderPayment(uuid, paymentMethod, paymentStatus);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reports")
    public ResponseEntity<OrderReportResponse> getOrderReports(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        System.out.println("Received startDate: " + startDate);
        System.out.println("Received endDate: " + endDate);
        try {
            OrderReportResponse report = orderService.getOrderReports(startDate, endDate);
            return new ResponseEntity<>(report, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi để dễ dàng gỡ lỗi
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build(); // Trả về 204 No Content
    }

}
