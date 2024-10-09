package com.onestep.business_management.Controller;

import com.onestep.business_management.DTO.API.ApiResponse;
import com.onestep.business_management.DTO.OrderDTO.OrderReportResponse;
import com.onestep.business_management.DTO.OrderDTO.OrderRequest;
import com.onestep.business_management.DTO.OrderDTO.OrderResponse;
import com.onestep.business_management.Entity.OrderOffline;
import com.onestep.business_management.Exeption.ResourceNotFoundException;
import com.onestep.business_management.Repository.OrderOfflineRepository;
import com.onestep.business_management.Service.OrderService.OrderMapper;
import com.onestep.business_management.Service.OrderService.OrderService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/seller/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderOfflineRepository orderOfflineRepository;

    @GetMapping
    public ResponseEntity<?> getAllOrder() {
        try {
            List<OrderResponse> danhSachDonHang = orderService.getAllOrders();
            ApiResponse<List<OrderResponse>> phanHoiApi = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Đã lấy tất cả đơn hàng thành công",
                    danhSachDonHang,
                    LocalDateTime.now());
            return new ResponseEntity<>(phanHoiApi, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy đơn hàng: " + e.getMessage());
            ApiResponse<String> phanHoiLoi = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Đã xảy ra lỗi khi lấy đơn hàng: " + e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(phanHoiLoi, HttpStatus.INTERNAL_SERVER_ERROR);
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
            // Cập nhật thông tin thanh toán
            OrderResponse updatedOrder = orderService.updateOrderPayment(uuid, paymentMethod, paymentStatus);

            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/stores/{storeId}")
    public ResponseEntity<?> getAllOrdersByStoreId(@PathVariable String storeId) {
        try {
            UUID uuid = UUID.fromString(storeId);
            List<OrderResponse> orders = orderService.getAllOrdersByStoreId(uuid);
            ApiResponse<List<OrderResponse>> phanHoiApi = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Đã lấy tất cả đơn hàng của cửa hàng thành công",
                    orders,
                    LocalDateTime.now());
            return new ResponseEntity<>(phanHoiApi, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Xử lý UUID không hợp lệ
            System.out.println("UUID cửa hàng không hợp lệ: " + e.getMessage());
            ApiResponse<String> phanHoiLoi = new ApiResponse<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "UUID cửa hàng không hợp lệ: " + e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(phanHoiLoi, HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            // Xử lý trường hợp không tìm thấy đơn hàng cho cửa hàng
            System.out.println("Không tìm thấy đơn hàng cho cửa hàng: " + e.getMessage());
            ApiResponse<String> phanHoiLoi = new ApiResponse<>(
                    HttpStatus.NOT_FOUND.value(),
                    e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(phanHoiLoi, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Xử lý các lỗi khác
            System.out.println("Lỗi khi lấy đơn hàng của cửa hàng: " + e.getMessage());
            ApiResponse<String> phanHoiLoi = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Đã xảy ra lỗi khi lấy đơn hàng của cửa hàng: " + e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(phanHoiLoi, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse<String>> deleteOrder(@PathVariable String orderId) {
        try {
            UUID uuid = UUID.fromString(orderId);
            orderService.deleteOrder(uuid);

            ApiResponse<String> response = new ApiResponse<>(
                    HttpStatus.NO_CONTENT.value(),
                    "Đã xóa đơn hàng thành công.",
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            // Handle invalid UUID format
            ApiResponse<String> errorResponse = new ApiResponse<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "UUID đơn hàng không hợp lệ: " + e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            // Handle case where order is not found
            ApiResponse<String> errorResponse = new ApiResponse<>(
                    HttpStatus.NOT_FOUND.value(),
                    e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Handle other exceptions
            ApiResponse<String> errorResponse = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Đã xảy ra lỗi khi xóa đơn hàng: " + e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
