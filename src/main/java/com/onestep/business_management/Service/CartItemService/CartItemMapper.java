package com.onestep.business_management.Service.CartItemService;

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

    default CartItems cartItemRequestToEntity(CartItemRequest cartItemRequest, @Context MapperService mapperService){

        if(cartItemRequest == null){
            return null;
        }

        CartItems cartItems = new CartItems();

        if(cartItemRequest.getProductId() != null){
            Product product = mapperService.findProductById(cartItemRequest.getProductId());
            if(product == null){
                throw new ResourceNotFoundException("Product not found" + product);
            }
            cartItems.setProduct(product);
        }

        cartItems.setQuantity(cartItemRequest.getQuantity());

       cartItems.setPrice(cartItemRequest.getPrice());

        cartItems.setTotalPrice(cartItemRequest.getPrice() * cartItemRequest.getQuantity());

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
