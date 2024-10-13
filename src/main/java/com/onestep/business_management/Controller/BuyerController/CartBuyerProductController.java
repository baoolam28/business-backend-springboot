package com.onestep.business_management.Controller.BuyerController;

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


    @GetMapping("/{id}")
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
    

    @PostMapping
    public ResponseEntity<?> createCart(@RequestBody CartRequest cartRequest){
        try {
            CartResponse response = cartService.createCartByUserId(cartRequest.getUserId(), cartRequest);
            ApiResponse<CartResponse> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Create cart retrieved successfully",
                    response,
                    LocalDateTime.now()
            ) ;
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
           System.out.println("Error retrieving cart: " + e.getMessage());
           ApiResponse errorResponse = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
           return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/{id}")
    public ResponseEntity<?> addProductToCart(@PathVariable("id") Integer cartId, @RequestBody CartItemRequest cartItemRequest){
        try {
            CartResponse response = cartService.addProductToCart(cartId, cartItemRequest);
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

    @DeleteMapping("/{id}/product/{productId}")
    public ResponseEntity<?> deleteProductFromCart(@PathVariable("id") Integer cartId, @PathVariable("productId") Integer productId){
        try {
            CartResponse response = cartService.deleteProductFromCart(cartId, productId);
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
}
