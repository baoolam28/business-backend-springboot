package com.onestep.business_management.Service.CartService;

import com.onestep.business_management.DTO.CartDTO.CartRequest;
import com.onestep.business_management.DTO.CartDTO.CartResponse;
import com.onestep.business_management.DTO.CartItemDTO.CartItemRequest;
import com.onestep.business_management.DTO.CartItemDTO.CartItemResponse;
import com.onestep.business_management.Entity.Cart;
import com.onestep.business_management.Entity.CartItems;
import com.onestep.business_management.Entity.Product;
import com.onestep.business_management.Service.ProductService.ProductService;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.ArrayList;

@Mapper
public interface CartMapper {
    
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    // Mapping CartRequest to Cart Entity
    @Mappings({
        @Mapping(target = "cartItems", source = "cartItems", qualifiedByName = "mapCartItemRequestsToEntities")
    })
    Cart toEntity(CartRequest cartRequest);

    // Mapping Cart Entity to CartResponse
    @Mappings({
        @Mapping(target = "cartItems", source = "cartItems", qualifiedByName = "mapCartItemsToResponses"),
        @Mapping(source = "user.userId", target = "userId")
    })
    CartResponse toResponse(Cart cart);

    // Custom method to map List<CartItemRequest> to List<CartItem>
    @Named("mapCartItemRequestsToEntities")
    default List<CartItems> mapCartItemRequestsToEntities(List<CartItemRequest> cartItemRequests) {
        if(cartItemRequests == null || cartItemRequests.isEmpty()){
            return new ArrayList<>();
        }
        
        return cartItemRequests.stream().map(request -> {
            
            CartItems cartItem = new CartItems();
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(request.getPrice());

            return cartItem;
        }).toList();
    }

    // Custom method to map List<CartItem> to List<CartItemResponse>
    @Named("mapCartItemsToResponses")
    default List<CartItemResponse> mapCartItemsToResponses(List<CartItems> cartItems) {
        
        return cartItems.stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            CartItemResponse response = new CartItemResponse();
            if(product != null){
                response.setProductId(product.getProductId());
                response.setProductName(product.getProductName());
                response.setBarcode(product.getBarcode());
            }
            response.setQuantity(cartItem.getQuantity());
            response.setPrice(cartItem.getPrice());
            response.setTotalPrice(cartItem.getPrice() * cartItem.getQuantity());
            return response;
        }).toList();
    }
}
