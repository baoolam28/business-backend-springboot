package com.onestep.business_management.Controller.BuyerController;

import com.onestep.business_management.Exeption.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.onestep.business_management.DTO.API.ApiResponse;
import com.onestep.business_management.DTO.CartDTO.CartRequest;
import com.onestep.business_management.DTO.CartDTO.CartResponse;
import com.onestep.business_management.DTO.CartItemDTO.CartItemRequest;
import com.onestep.business_management.DTO.ProductDTO.ProductResponse;
import com.onestep.business_management.Service.CartService.CartService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/buyer/cart")
public class CartBuyerProductController {
    @Autowired
    private CartService cartService;


    @GetMapping("/get-by-user/{id}")
    public ResponseEntity<?> getCartById(@PathVariable("id") UUID userId) {
        try {
            CartResponse response = cartService.getCartByUserId(userId);
            ApiResponse<CartResponse> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Cart retrieved successfully",
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
    

    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addProductToCart(@RequestBody CartRequest cartRequest){
        try {
            CartResponse response = cartService.addProductToCart(cartRequest);
            ApiResponse<CartResponse> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Product added to cart retrieved successfully",
                    response,
                    LocalDateTime.now()
            );
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
           System.out.println("Error retrieving cart: " + e.getMessage());
           ApiResponse errorResponse = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
           return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-product")
    public ResponseEntity<?> deleteProductFromCart(@RequestBody CartRequest cartRequest){
        try {
            CartResponse response = cartService.deleteProductFromCart(cartRequest);
            ApiResponse<CartResponse> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Product deleted from cart successfully",
                    response,
                    LocalDateTime.now()
            );
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
           System.out.println("Error retrieving cart: " + e.getMessage());
           ApiResponse errorResponse = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
           return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/update-cart")
    public ResponseEntity<?> updateCart(@RequestBody CartRequest cartRequest) {
        try {
            // Gọi service để cập nhật giỏ hàng
            CartResponse response = cartService.updateCart(cartRequest);

            // Tạo đối tượng ApiResponse cho kết quả thành công
            ApiResponse<CartResponse> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Cart updated successfully",
                    response,
                    LocalDateTime.now()
            );

            // Trả về kết quả thành công
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            // Xử lý trường hợp không tìm thấy tài nguyên
            ApiResponse<String> errorResponse = new ApiResponse<>(
                    HttpStatus.NOT_FOUND.value(),
                    e.getMessage()
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Xử lý các ngoại lệ khác
            System.out.println("Error updating cart: " + e.getMessage());
            ApiResponse<String> errorResponse = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An error occurred while updating the cart"
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
