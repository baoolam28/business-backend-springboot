package com.onestep.business_management.Service.CartService;


import com.onestep.business_management.DTO.CartItemDTO.CartItemRequest;
import com.onestep.business_management.DTO.CartItemDTO.CartUpdateRequest;
import com.onestep.business_management.DTO.CartItemDTO.ItemsRequest;
import com.onestep.business_management.Entity.ProductDetail;
import com.onestep.business_management.Repository.ProductDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import com.onestep.business_management.DTO.CartDTO.CartRequest;
import com.onestep.business_management.DTO.CartDTO.CartResponse;
import com.onestep.business_management.Entity.Cart;
import com.onestep.business_management.Entity.CartItems;
import com.onestep.business_management.Entity.User;
import com.onestep.business_management.Exeption.ResourceAlreadyExistsException;
import com.onestep.business_management.Exeption.ResourceNotFoundException;
import com.onestep.business_management.Repository.CartRepository;
import com.onestep.business_management.Repository.ProductRepository;
import com.onestep.business_management.Repository.UserRepository;
import com.onestep.business_management.Service.CartItemService.CartItemMapper;
import com.onestep.business_management.Utils.MapperService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MapperService mapperService;

    @Autowired
    private ProductDetailRepository productDetailRepository;

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




    public CartResponse updateCart(CartUpdateRequest cartRequest) {
        // 1. Tìm người dùng theo userId
        User user = userRepository.findById(cartRequest.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("User not found: " + cartRequest.getUserId())
        );

        // 2. Tìm giỏ hàng của người dùng, nếu không có thì báo lỗi
        Cart cart = cartRepository.findByUser(user).orElseThrow(
                () -> new ResourceNotFoundException("Cart not found for userId: " + cartRequest.getUserId())
        );

        List<ItemsRequest> itemsRequest = cartRequest.getCartItems();
        List<CartItems> cartItems = cart.getCartItems();

        // 3. Nếu cartItems trong request là mảng rỗng, xóa hết sản phẩm trong giỏ hàng
        if (itemsRequest.isEmpty()) {
            cartItems.clear(); // Xóa tất cả các sản phẩm trong giỏ hàng
        } else {
            // 4. Lưu danh sách các productDetailId trong request để kiểm tra sản phẩm bị xóa
            List<Integer> updatedProductDetailIds = itemsRequest.stream()
                    .map(ItemsRequest::getProductDetailId)
                    .collect(Collectors.toList());

            // 5. Duyệt qua các sản phẩm trong giỏ hàng và xử lý cập nhật/xóa
            for (Iterator<CartItems> iterator = cartItems.iterator(); iterator.hasNext(); ) {
                CartItems item = iterator.next();
                ProductDetail prodDetail = item.getProductDetail();

                // Kiểm tra nếu sản phẩm trong giỏ hàng không có trong yêu cầu cập nhật => xóa
                if (!updatedProductDetailIds.contains(prodDetail.getProductDetailId())) {
                    iterator.remove(); // Xóa sản phẩm này khỏi giỏ hàng
                } else {
                    // Cập nhật số lượng cho các sản phẩm có trong yêu cầu
                    ItemsRequest itemReq = findItemRequestById(item.getProductDetail().getProductDetailId(), itemsRequest);
                    if (itemReq != null) {
                        item.setQuantity(itemReq.getQuantity());
                    }
                }
            }

            // 6. Thêm sản phẩm mới vào giỏ hàng nếu nó không có trong giỏ
            for (ItemsRequest itemReq : itemsRequest) {
                boolean itemExists = cartItems.stream()
                        .anyMatch(item -> item.getProductDetail().getProductDetailId() == itemReq.getProductDetailId());

                if (!itemExists) {
                    CartItems newItem = createNewItem(itemReq, cart);
                    cartItems.add(newItem);
                }
            }
        }

        // 7. Lưu giỏ hàng đã cập nhật
        Cart updatedCart = cartRepository.save(cart);

        // 8. Trả về thông tin giỏ hàng đã cập nhật
        return CartMapper.INSTANCE.toResponse(updatedCart);
    }

    private ItemsRequest findItemRequestById(Integer productDetailId, List<ItemsRequest> itemsRequest) {
        return itemsRequest.stream()
                .filter(item -> item.getProductDetailId().equals(productDetailId))
                .findFirst()
                .orElse(null);
    }

    private CartItems createNewItem(ItemsRequest request, Cart cart) {
        ProductDetail prodDetail = productDetailRepository.findById(request.getProductDetailId()).orElseThrow(
                () -> new ResourceNotFoundException("Product detail not found!")
        );

        CartItems cartItem = new CartItems();
        cartItem.setCart(cart);
        cartItem.setProductDetail(prodDetail);
        cartItem.setQuantity(request.getQuantity());

        return cartItem;
    }


}


