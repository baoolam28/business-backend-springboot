package com.onestep.business_management.Controller.SellerController;

import com.onestep.business_management.DTO.API.ApiResponse;
import com.onestep.business_management.DTO.OrderDTO.OrderDetailRequest;
import com.onestep.business_management.DTO.OrderDTO.OrderDetailResponse;
import com.onestep.business_management.DTO.OrderDTO.OrderOnlineResponse;
import com.onestep.business_management.DTO.OrderDTO.OrderReportResponse;
import com.onestep.business_management.DTO.OrderDTO.OrderRequest;
import com.onestep.business_management.DTO.OrderDTO.OrderResponse;
import com.onestep.business_management.DTO.OrderDTO.OrderStatusRequest;
import com.onestep.business_management.DTO.PaymentDTO.PaymentUpdateRequest;
import com.onestep.business_management.Entity.OrderOffline;
import com.onestep.business_management.Entity.OrderOnline;
import com.onestep.business_management.Exeption.ResourceNotFoundException;
import com.onestep.business_management.Repository.OrderOfflineRepository;
import com.onestep.business_management.Service.OrderOnlineService.OrderOnlineService;
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
public class OrderSellerController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderOnlineService orderOnlineService;

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
    public ResponseEntity<?> getOrderById(@PathVariable String orderId) {
        UUID uuid = UUID.fromString(orderId);
        try {

            OrderResponse response = orderService.getOrderById(uuid);
            ApiResponse<OrderResponse> phanHoiApi = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Đã lấy tất cả đơn hàng thành công",
                    response,
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

    @PutMapping("/{orderId}/payment")
    public ResponseEntity<?> updateOrderPayment(
            @PathVariable UUID orderId,
            @RequestBody PaymentUpdateRequest request) {

        try {
            // Gọi service để cập nhật thanh toán
            OrderResponse updatedOrderResponse = orderService.updateOrderPayment(
                    orderId, request.getPaymentMethod(), request.isPaymentStatus());

            // Tạo phản hồi API thành công
            ApiResponse<OrderResponse> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Cập nhật thanh toán thành công",
                    updatedOrderResponse,
                    LocalDateTime.now());

            // Trả về phản hồi với mã trạng thái 200 OK
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật thanh toán: " + e.getMessage());

            // Tạo phản hồi API lỗi
            ApiResponse<String> errorResponse = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Đã xảy ra lỗi khi cập nhật thanh toán: " + e.getMessage(),
                    null,
                    LocalDateTime.now());

            // Trả về phản hồi với mã trạng thái 500 INTERNAL_SERVER_ERROR
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
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

    @GetMapping("/{orderId}/products")
    public ResponseEntity<ApiResponse<List<OrderDetailResponse>>> getProductsByOrderId(@PathVariable String orderId) {
        UUID uuid = UUID.fromString(orderId);
        try {
            List<OrderDetailResponse> products = orderService.getProductsByOrderId(uuid);
            ApiResponse<List<OrderDetailResponse>> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Đã lấy sản phẩm từ đơn hàng thành công.",
                    products,
                    LocalDateTime.now());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<OrderDetailResponse>> errorResponse = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Đã xảy ra lỗi khi lấy sản phẩm từ đơn hàng: " + e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{orderId}")
    public ResponseEntity<?> updateOrderDetail(
            @RequestBody OrderRequest request,
            @PathVariable String orderId) {

        try {
            UUID uuid = UUID.fromString(orderId);
            // Gọi service để cập nhật thanh toán
            System.out.println("loi " + request.toString());

            OrderResponse updatedOrderResponse = orderService.updateOrderDetail(request, uuid);

            // Tạo phản hồi API thành công
            ApiResponse<OrderResponse> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Cập nhật thành công",
                    updatedOrderResponse,
                    LocalDateTime.now());

            // Trả về phản hồi với mã trạng thái 200 OK
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhập hoá đơn: " + e.getMessage());

            // Tạo phản hồi API lỗi
            ApiResponse<String> errorResponse = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Đã xảy ra lỗi khi cập nhật: " + e.getMessage(),
                    null,
                    LocalDateTime.now());

            // Trả về phản hồi với mã trạng thái 500 INTERNAL_SERVER_ERROR
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/offline/{storeId}")
    public ResponseEntity<?> getAllOrdersOfflineByStoreId(@PathVariable String storeId) {
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

    @GetMapping("/online/{storeId}")
    public ResponseEntity<?> getAllOrdersByStoreId(@PathVariable UUID storeId) {
        try {
            List<OrderOnlineResponse> orders = orderOnlineService.getAllOrdersByStoreId(storeId);

            ApiResponse<List<OrderOnlineResponse>> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Đã lấy tất cả đơn hàng của cửa hàng thành công",
                    orders,
                    LocalDateTime.now());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            // Xử lý UUID không hợp lệ
            ApiResponse<String> errorResponse = new ApiResponse<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "UUID cửa hàng không hợp lệ: " + e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        } catch (ResourceNotFoundException e) {
            // Xử lý trường hợp không tìm thấy đơn hàng cho cửa hàng
            ApiResponse<String> errorResponse = new ApiResponse<>(
                    HttpStatus.NOT_FOUND.value(),
                    e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            // Xử lý các lỗi khác
            ApiResponse<String> errorResponse = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Đã xảy ra lỗi khi lấy đơn hàng của cửa hàng: " + e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/online/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable String orderId,
            @RequestBody OrderStatusRequest orderStatusRequest) {
        try {

            System.out.println("update status request: "+orderStatusRequest.toString());
            // Gọi service để cập nhật trạng thái đơn hàng
            OrderOnlineResponse response = orderOnlineService.updateOrderStatus(orderId, orderStatusRequest);

            // Tạo phản hồi thành công
            ApiResponse<OrderOnlineResponse> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Trạng thái đơn hàng đã được cập nhật thành công",
                    response,
                    LocalDateTime.now());

            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Xử lý UUID không hợp lệ
            ApiResponse<String> errorResponse = new ApiResponse<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "UUID đơn hàng không hợp lệ: " + e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            // Xử lý trường hợp không tìm thấy đơn hàng
            ApiResponse<String> errorResponse = new ApiResponse<>(
                    HttpStatus.NOT_FOUND.value(),
                    e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Xử lý các lỗi khác
            ApiResponse<String> errorResponse = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Đã xảy ra lỗi khi cập nhật trạng thái đơn hàng: " + e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/online/{storeId}/status")
    public ResponseEntity<?> updateOrderStatusByStoreId(
            @PathVariable String storeId,
            @RequestBody OrderStatusRequest orderStatusRequest) {
        try {
            UUID uuid = UUID.fromString(storeId);
            // Giả định rằng bạn muốn cập nhật trạng thái cho tất cả các đơn hàng với trạng
            // thái hiện tại nhất định
            // Bạn có thể thay đổi logic tùy theo yêu cầu của bạn
            int updatedCount = orderOnlineService.updateOrderStatusByStoreID(
                    OrderOnline.Status.DANG_DONG_GOI, // Trạng thái hiện tại mà bạn cần thay đổi
                    orderStatusRequest.getStatus(), // Trạng thái mới
                    uuid);

            ApiResponse<Integer> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Đã cập nhật trạng thái của " + updatedCount + " đơn hàng thành công",
                    updatedCount,
                    LocalDateTime.now());

            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<String> errorResponse = new ApiResponse<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "UUID cửa hàng không hợp lệ: " + e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            ApiResponse<String> errorResponse = new ApiResponse<>(
                    HttpStatus.NOT_FOUND.value(),
                    e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse<String> errorResponse = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Đã xảy ra lỗi khi cập nhật trạng thái đơn hàng: " + e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
