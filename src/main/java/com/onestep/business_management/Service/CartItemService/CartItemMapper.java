package com.onestep.business_management.Service.CartItemService;

import com.onestep.business_management.DTO.CartDTO.CartRequest;
import com.onestep.business_management.Entity.ProductDetail;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Context;

import com.onestep.business_management.DTO.CartItemDTO.CartItemRequest;
import com.onestep.business_management.DTO.CartItemDTO.CartItemResponse;
import com.onestep.business_management.Entity.Cart;
import com.onestep.business_management.Utils.MapperService;
import com.onestep.business_management.Entity.CartItems;
import com.onestep.business_management.Entity.Product;
import com.onestep.business_management.Exeption.ResourceNotFoundException;

@Mapper
public interface CartItemMapper {
    CartItemMapper INSTANCE = Mappers.getMapper(CartItemMapper.class);

    default CartItems cartItemRequestToEntity(CartRequest cartRequest, @Context MapperService mapperService){

        if(cartRequest == null){
            return null;
        }

        CartItems cartItems = new CartItems();

        if(cartRequest.getProductDetailId() != null){
            ProductDetail detail = mapperService.findProductDetailById(cartRequest.getProductDetailId());
            cartItems.setProductDetail(detail);
        }

        cartItems.setQuantity(cartRequest.getQuantity());

        return cartItems;
    }


    default CartItemResponse cartItemToResponse(CartItems cartItems){
        if(cartItems == null){
            return null;
        }

        CartItemResponse cartItemResponse = new CartItemResponse();

        if(cartItems.getProduct() != null){
            Product product = cartItems.getProduct();

            cartItemResponse.setProductId(product.getProductId());
            cartItemResponse.setProductName(product.getProductName());
            cartItemResponse.setBarcode(product.getBarcode());
        }

        cartItemResponse.setPrice(cartItems.getPrice());
        cartItemResponse.setQuantity(cartItems.getQuantity());
        cartItemResponse.setTotalPrice(cartItems.getTotalPrice());

        return cartItemResponse;

    }
}
