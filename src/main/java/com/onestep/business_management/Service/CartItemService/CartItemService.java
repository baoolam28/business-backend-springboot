package com.onestep.business_management.Service.CartItemService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onestep.business_management.DTO.CartItemDTO.CartItemRequest;
import com.onestep.business_management.DTO.CartItemDTO.CartItemResponse;
import com.onestep.business_management.Entity.Cart;
import com.onestep.business_management.Entity.CartItems;
import com.onestep.business_management.Repository.CartItemsRepository;
import com.onestep.business_management.Repository.CartRepository;
import com.onestep.business_management.Utils.MapperService;

@Service
public class CartItemService {
    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MapperService mapperService;



    public CartItemResponse getCartItemById(Integer itemId){
        Optional<CartItems> cartItems = cartItemsRepository.findById(itemId);

        if(cartItems.isEmpty()){
            throw new IllegalArgumentException("Cart item not found with id:" + itemId);
        }

        return CartItemMapper.INSTANCE.cartItemToResponse(cartItems.get());
    }

    public CartItemResponse updateCartItem(Integer itemId, CartItemRequest cartItemRequest){
        Optional<CartItems> cartItemOpt = cartItemsRepository.findById(itemId);
        if (cartItemOpt.isEmpty()) {
            throw new IllegalArgumentException("Cart item not found with id: " + itemId);
        }
        CartItems cartItems = cartItemOpt.get();

        if (cartItemRequest.getProductId() != null) {
            cartItems.setProduct(mapperService.findProductById(cartItemRequest.getProductId()));
        }
    
        cartItems.setQuantity(cartItemRequest.getQuantity());
    
        // Cập nhật giá và tổng giá trị
        Double productPrice = cartItems.getProduct().getPrice();
        cartItems.setPrice(productPrice);
        cartItems.setTotalPrice(productPrice * cartItemRequest.getQuantity());
    
        // Lưu thay đổi vào cơ sở dữ liệu
        CartItems updatedCartItem = cartItemsRepository.save(cartItems);
    
        // Chuyển đổi Entity thành DTO
        return CartItemMapper.INSTANCE.cartItemToResponse(updatedCartItem);
    }

    public void deleteCartItem(Integer itemId){
        Optional<CartItems> cartItemOpt = cartItemsRepository.findById(itemId);
        if (cartItemOpt.isEmpty()) {
            throw new IllegalArgumentException("Cart item not found with id: " + itemId);
        }

        // Xóa mục trong giỏ hàng khỏi database
        cartItemsRepository.delete(cartItemOpt.get());
    }

}
