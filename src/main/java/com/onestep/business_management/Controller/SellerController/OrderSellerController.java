package com.onestep.business_management.Controller.SellerController;

import com.onestep.business_management.DTO.API.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;

import com.onestep.business_management.DTO.OrderDTO.OrderOnlineResponse;
import com.onestep.business_management.DTO.OrderDTO.OrderStatusRequest;
import com.onestep.business_management.Entity.OrderOnline;
import com.onestep.business_management.Exeption.ResourceNotFoundException;
import com.onestep.business_management.Service.OrderOnlineService.OrderOnlineService;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/seller/orders-online")
public class OrderSellerController {

    @Autowired
    private OrderOnlineService orderOnlineService;

    @GetMapping("/stores/{storeId}")
    public ResponseEntity<?> getAllOrdersByStoreId(@PathVariable String storeId) {
        try {
            UUID uuid = UUID.fromString(storeId);
            List<OrderOnlineResponse> orders = orderOnlineService.getAllOrdersByStoreId(uuid);

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

    @PutMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable String orderId,
            @RequestBody OrderStatusRequest orderStatusRequest) {
        try {
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

    @PutMapping("/stores/{storeId}/status")
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
