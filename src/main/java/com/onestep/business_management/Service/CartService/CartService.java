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
            () -> new ResourceNotFoundException("Cart with userId = " + userId + " not found!")
        );

        return CartMapper.INSTANCE.toResponse(cart);
        
    }


    public CartResponse addProductToCart(CartRequest cartRequest) {
        // 1. Tìm người dùng theo userId
        User user = userRepository.findById(cartRequest.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("User not found: " + cartRequest.getUserId())
        );

        // 2. Tìm giỏ hàng của người dùng, nếu không có thì tạo mới
        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return newCart;
        });

        // 3. Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        cart.getCartItems().stream()
                .filter(item -> item.getProductDetail().getProductDetailId().equals(cartRequest.getProductDetailId()))
                .findFirst()
                .ifPresentOrElse(
                        // Nếu sản phẩm đã có, cập nhật số lượng
                        existingCartItem -> existingCartItem.setQuantity(existingCartItem.getQuantity() + cartRequest.getQuantity()),
                        // Nếu chưa có, tạo mới CartItem và thêm vào giỏ hàng
                        () -> {
                            CartItems newCartItem = CartItemMapper.INSTANCE.cartItemRequestToEntity(cartRequest, mapperService);
                            newCartItem.setCart(cart);
                            cart.getCartItems().add(newCartItem);
                        }
                );

        // 4. Lưu giỏ hàng đã cập nhật lại
        Cart updatedCart = cartRepository.save(cart);

        // 5. Trả về thông tin giỏ hàng đã cập nhật
        return CartMapper.INSTANCE.toResponse(updatedCart);
    }



    public CartResponse deleteProductFromCart(CartRequest cartRequest){

        User user = userRepository.findById(cartRequest.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("User not found: " + cartRequest.getUserId())
        );

        Cart cart = cartRepository.findByUser(user).orElseThrow(
                () -> new ResourceAlreadyExistsException("Cart not found with user id: " + cartRequest.getUserId())
        );

        CartItems cartItems = cart.getCartItems().stream()
                .filter(item -> item.getProductDetail().getProductDetailId().equals(cartRequest.getProductDetailId()))
                .findFirst().orElseThrow(
                    () -> new ResourceNotFoundException("Product not found in cart: " + cartRequest.getProductDetailId())
                );

        cart.getCartItems().remove(cartItems);

        Cart updateCartItem = cartRepository.save(cart);

        return CartMapper.INSTANCE.toResponse(updateCartItem);
    }

    public CartResponse updateCart(CartRequest cartRequest) {
        // 1. Tìm người dùng theo userId
        User user = userRepository.findById(cartRequest.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("User not found: " + cartRequest.getUserId())
        );

        // 2. Tìm giỏ hàng của người dùng, nếu không có thì báo lỗi
        Cart cart = cartRepository.findByUser(user).orElseThrow(
                () -> new ResourceNotFoundException("Cart not found for userId: " + cartRequest.getUserId())
        );

        // 3. Tìm sản phẩm trong giỏ hàng
        Optional<CartItems> optionalCartItem = cart.getCartItems().stream()
                .filter(item -> item.getProductDetail().getProductDetailId().equals(cartRequest.getProductDetailId()))
                .findFirst();

        if (optionalCartItem.isPresent()) {
            // 4. Nếu sản phẩm đã có, cập nhật số lượng
            CartItems cartItem = optionalCartItem.get();
            if (cartRequest.getQuantity() > 0) {
                // Cập nhật số lượng nếu lớn hơn 0
                cartItem.setQuantity(cartRequest.getQuantity());
            } else {
                // Xóa sản phẩm khỏi giỏ hàng nếu số lượng bằng 0
                cart.getCartItems().remove(cartItem);
            }
        } else {
            // 5. Nếu sản phẩm chưa có và số lượng lớn hơn 0, thêm mới CartItem
            if (cartRequest.getQuantity() > 0) {
                CartItems newCartItem = CartItemMapper.INSTANCE.cartItemRequestToEntity(cartRequest, mapperService);
                newCartItem.setCart(cart);
                cart.getCartItems().add(newCartItem);
            } else {
                throw new ResourceNotFoundException("Cannot add product with quantity 0 to the cart.");
            }
        }

        // 6. Lưu giỏ hàng đã cập nhật lại
        Cart updatedCart = cartRepository.save(cart);

        // 7. Trả về thông tin giỏ hàng đã cập nhật
        return CartMapper.INSTANCE.toResponse(updatedCart);
    }

}
