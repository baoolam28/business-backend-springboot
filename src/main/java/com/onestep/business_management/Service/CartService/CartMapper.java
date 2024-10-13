package com.onestep.business_management.Service.CartService;

import com.onestep.business_management.DTO.CartDTO.CartRequest;
import com.onestep.business_management.DTO.CartDTO.CartResponse;
import com.onestep.business_management.DTO.CartItemDTO.CartItemRequest;
import com.onestep.business_management.DTO.CartItemDTO.CartItemResponse;
import com.onestep.business_management.Entity.Cart;
import com.onestep.business_management.Entity.CartItems;
import com.onestep.business_management.Entity.Product;
import com.onestep.business_management.Entity.ProductDetail;
import com.onestep.business_management.Service.ProductService.ProductService;

import com.onestep.business_management.Utils.MapperService;
import com.onestep.business_management.Utils.StringToMapConverter;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.ArrayList;

@Mapper
public interface CartMapper {

    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    // Mapping CartRequest to Cart Entity

    // Mapping Cart Entity to CartResponse
    @Mappings({
            @Mapping(target = "cartItems", source = "cartItems", qualifiedByName = "mapCartItemsToResponses"),
            @Mapping(source = "user.userId", target = "userId")
    })
    CartResponse toResponse(Cart cart);

    // Custom method to map List<CartItemRequest> to List<CartItems>


    // Custom method to map List<CartItems> to List<CartItemResponse>
    @Named("mapCartItemsToResponses")
    default List<CartItemResponse> mapCartItemsToResponses(List<CartItems> cartItems) {
        if (cartItems == null) {
            return new ArrayList<>();
        }

        return cartItems.stream().map(cartItem -> {
            ProductDetail productDetail = cartItem.getProductDetail();
            Product product = productDetail != null ? productDetail.getProduct() : null;

            CartItemResponse response = new CartItemResponse();
            if (productDetail != null) {
                response.setProductDetailId(productDetail.getProductDetailId());
                response.setProductName(product != null ? product.getProductName() : null);
                response.setQuantity(cartItem.getQuantity());
                response.setPrice(productDetail.getPrice());
                response.setTotalPrice(productDetail.getPrice() * cartItem.getQuantity());
                response.setImage(productDetail.getImage());

                // Convert attributes from string to map
                response.setAttributes(StringToMapConverter.convertStringToMap(productDetail.getAttributes()));
            }
            return response;
        }).toList();
    }
}
