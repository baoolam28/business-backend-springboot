package com.onestep.business_management.Service.CartItemService;

import com.onestep.business_management.DTO.CartDTO.CartRequest;
import com.onestep.business_management.Entity.*;
import com.onestep.business_management.Utils.StringToMapConverter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Context;

import com.onestep.business_management.DTO.CartItemDTO.CartItemRequest;
import com.onestep.business_management.DTO.CartItemDTO.CartItemResponse;
import com.onestep.business_management.Utils.MapperService;
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


    default CartItemResponse cartItemToResponse(CartItems cartItems, @Context MapperService mapperService){
        if(cartItems == null){
            return null;
        }

        CartItemResponse cartItemResponse = new CartItemResponse();

        ProductDetail productDetail = cartItems.getProductDetail();

        if(productDetail == null){
            return null;
        }

        cartItemResponse.setProductDetailId(productDetail.getProductDetailId());

        Product product = productDetail.getProduct();
        Store store = product.getStore();
        cartItemResponse.setStoreId(store.getStoreId());
        cartItemResponse.setProductName(product.getProductName());
        cartItemResponse.setPrice(productDetail.getPrice());
        cartItemResponse.setQuantity(cartItems.getQuantity());
        cartItemResponse.setTotalPrice(cartItems.getQuantity() * productDetail.getPrice());
        cartItemResponse.setImage(productDetail.getImage());
        cartItemResponse.setAttributes(StringToMapConverter.convertStringToMap(productDetail.getAttributes()));

        return cartItemResponse;

    }
}
