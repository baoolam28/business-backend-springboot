package com.onestep.business_management.Service.CartService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onestep.business_management.DTO.CartDTO.CartRequest;
import com.onestep.business_management.DTO.CartDTO.CartResponse;
import com.onestep.business_management.DTO.CartItemDTO.CartItemRequest;
import com.onestep.business_management.Entity.Cart;
import com.onestep.business_management.Entity.CartItems;
import com.onestep.business_management.Entity.Product;
import com.onestep.business_management.Entity.User;
import com.onestep.business_management.Exeption.ResourceAlreadyExistsException;
import com.onestep.business_management.Exeption.ResourceNotFoundException;
import com.onestep.business_management.Repository.CartRepository;
import com.onestep.business_management.Repository.ProductRepository;
import com.onestep.business_management.Repository.UserRepository;
import com.onestep.business_management.Service.CartItemService.CartItemMapper;
import com.onestep.business_management.Service.CartItemService.CartItemService;
import com.onestep.business_management.Utils.MapperService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MapperService mapperService;

    public CartResponse getCartByUserId(UUID userId){
        User user = userRepository.findById(userId).orElseThrow(
            () -> new ResourceNotFoundException("user not found" + userId)
        );

        Cart cart = cartRepository.findByUser(user).orElseThrow(
            () -> new ResourceNotFoundException("user not found" + user.getFullName())
        );

        if(cart == null){
           throw new ResourceNotFoundException("Cart not found for user " + userId);
        }else{
            cart.getUser();
        }

        return CartMapper.INSTANCE.toResponse(cart);
        
    }

    public CartResponse createCartByUserId(UUID userId, CartRequest cartRequest){

        User user = userRepository.findById(userId).orElseThrow(
            () -> new ResourceNotFoundException("user not found" + userId)
        );

        // Kiểm tra xem giỏ hàng đã tồn tại cho user này chưa
        Optional<Cart> existingCart = cartRepository.findByUser(user);
        if(existingCart.isPresent()){
            throw new ResourceAlreadyExistsException("Cart already exists for this user: " + userId);
        }
            
        Cart cart = CartMapper.INSTANCE.toEntity(cartRequest);

        cart.setUser(user);

        Cart saveCart = cartRepository.save(cart);

        return CartMapper.INSTANCE.toResponse(saveCart);
        
    }

    public CartResponse addProductToCart(Integer cartId, CartItemRequest cartItemRequest){
        // 1. Tìm giỏ hàng theo cartId
        Cart cart = cartRepository.findById(cartId).orElseThrow(
            () -> new ResourceNotFoundException("Cart not found with id: " + cartId)
        );

        // 2. Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        Optional<CartItems> existingCartItem = cart.getCartItems().stream()
        .filter(item -> item.getProduct().getProductId().equals(cartItemRequest.getProductId()))
        .findFirst();

        if (existingCartItem.isPresent()) {
            // 3. Nếu sản phẩm đã có trong giỏ hàng, cập nhật số lượng và giá
            CartItems cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + cartItemRequest.getQuantity());
            cartItem.setPrice(cartItemRequest.getPrice());
            cartItem.setTotalPrice(cartItem.getQuantity() * cartItemRequest.getPrice());
        } else {
            // 4. Nếu sản phẩm chưa có, tạo mới một CartItem
            CartItems newCartItem = CartItemMapper.INSTANCE.cartItemRequestToEntity(cartItemRequest, mapperService);
            newCartItem.setCart(cart);  // Gán giỏ hàng cho CartItem
            cart.getCartItems().add(newCartItem);  // Thêm CartItem vào giỏ hàng
        }

         // 5. Lưu giỏ hàng đã cập nhật lại
         Cart updatedCart = cartRepository.save(cart);

         // 6. Trả về thông tin giỏ hàng đã cập nhật
         return CartMapper.INSTANCE.toResponse(updatedCart);
    }


    public CartResponse deleteProductFromCart(Integer cartId, Integer productId){

        Cart cart = cartRepository.findById(cartId).orElseThrow(
            () -> new ResourceNotFoundException("Cart not found with id: " + cartId)
        );

        CartItems cartItems = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst().orElseThrow(
                    () -> new ResourceNotFoundException("Product not found in cart: " + productId)
                );

        cart.getCartItems().remove(cartItems);

        //nếu CartItem rỗng thì xóa cart
        if(cart.getCartItems().isEmpty()){
            cartRepository.delete(cart);
            return null;
        }

        Cart updateCartItem = cartRepository.save(cart);

        return CartMapper.INSTANCE.toResponse(updateCartItem);
    } 
}
