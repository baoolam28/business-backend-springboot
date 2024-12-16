package com.onestep.business_management.Service.OrderOnlineService;

import com.onestep.business_management.DTO.OrderDTO.OrderOnlineDetailRequest;
import com.onestep.business_management.DTO.OrderDTO.OrderOnlineDetailResponse;
import com.onestep.business_management.DTO.OrderDTO.OrderOnlineResponse;
import com.onestep.business_management.DTO.OrderDTO.OrderShippingRespons;
import com.onestep.business_management.DTO.OrderDTO.OrderStatusRequest;
import com.onestep.business_management.Entity.*;
import com.onestep.business_management.Exeption.ResourceNotFoundException;
import com.onestep.business_management.Utils.MapperService;
import com.onestep.business_management.Utils.StringToMapConverter;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

@Mapper
public interface OrderOnlineMapper {
    OrderOnlineMapper INSTANCE = Mappers.getMapper(OrderOnlineMapper.class);



    @Named("mapToOrderResponse")
    default OrderOnlineResponse toOrderResponse(OrderOnline orderOnline){
        if(orderOnline == null ){
            return null;
        }
        OrderOnlineResponse response = new OrderOnlineResponse();
        response.setOrderId(orderOnline.getOrderOnlineId());
        response.setStatus(orderOnline.getStatus().toString());
        response.setOrderDate(orderOnline.getOrderDate());
        response.setPaymentStatus(orderOnline.isPaymentStatus());
        response.setPaymentMethod(orderOnline.getPaymentMethod());
        List<OrderOnlineDetail> orderDetails = orderOnline.getOrderDetails();
        List<OrderOnlineDetailResponse> orderOnlineDetailResponses = mapDetailsToResponses(orderDetails);
        response.setOrderDetails(orderOnlineDetailResponses);
        List<Shipment> shipments = orderOnline.getShipments();
        if(shipments.isEmpty()){
            return null;
        }
        ShippingAddress shippingAddress = shipments.get(0).getShippingAddress();
        response.setFullName(shippingAddress.getFullName());

        response.setPhone(shippingAddress.getPhoneNumber());
        response.setAddress(shippingAddress.getAddress());


        return response;
    };

    // Custom method to map List<OrderOnlineDetailRequest> to
    // List<OrderOnlineDetail>
    @Named("mapDetailRequestsToEntities")
    default List<OrderOnlineDetail> mapDetailRequestsToEntities(List<OrderOnlineDetailRequest> detailRequests,
            @Context MapperService mapperService) {
        return detailRequests.stream().map(detailRequest -> {
            OrderOnlineDetail detail = new OrderOnlineDetail();
            detail.setQuantity(detailRequest.getQuantity());
            ProductDetail productDetail = mapperService.findProductDetailById(detailRequest.getProductDetailId());
            if (productDetail == null) {
                throw new ResourceNotFoundException(
                        "ProductDetail not found for ID: " + detailRequest.getProductDetailId());
            }
            detail.setProductDetail(productDetail);
            detail.setPrice(productDetail.getPrice());
            return detail;
        }).toList();
    }

    @Named("mapDetailsToResponses")
    default List<OrderOnlineDetailResponse> mapDetailsToResponses(List<OrderOnlineDetail> details) {
        return details.stream().map(detail -> {
            ProductDetail productDetail = detail.getProductDetail();

            return OrderOnlineDetailResponse.builder()
                    .orderDetailId(detail.getOrderDetailId())
                    .quantity(detail.getQuantity())
                    .price(detail.getPrice())
                    .productDetailId(productDetail != null ? productDetail.getProductDetailId() : null)
                    .productName(productDetail != null && productDetail.getProduct() != null
                            ? productDetail.getProduct().getProductName()
                            : null)
                    .totalPrice(detail.calculateTotalPrice())
                    .image(productDetail != null ? productDetail.getImage() : null)
                    .attributes(productDetail != null
                            ? StringToMapConverter.convertStringToMap(productDetail.getAttributes())
                            : null)
                    .build();
        }).toList();
    }

    // Mapping Store ID to Store entity using MapperService
    @Named("mapStoreIdToStore")
    default Store mapStoreIdToStore(UUID storeId, @Context MapperService mapperService) {
        Store store = mapperService.findStoreById(storeId);
        if (store == null) {
            throw new IllegalArgumentException("Store not found for ID: " + storeId);
        }
        return store;
    }

    // Mapping User ID to User entity using MapperService
    @Named("mapUserIdToUser")
    default User mapUserIdToUser(UUID userId, @Context MapperService mapperService) {
        User user = mapperService.findUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found for ID: " + userId);
        }
        return user;
    }

    @Mapping(target = "status", source = "status")
    OrderOnline toOrderOnline(OrderStatusRequest orderStatusRequest, @Context MapperService mapperService);

}
