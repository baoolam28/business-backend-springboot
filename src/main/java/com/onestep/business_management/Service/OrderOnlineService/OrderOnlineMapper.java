package com.onestep.business_management.Service.OrderOnlineService;

import com.onestep.business_management.DTO.OrderDTO.OrderOnlineDetailRequest;
import com.onestep.business_management.DTO.OrderDTO.OrderOnlineDetailResponse;
import com.onestep.business_management.DTO.OrderDTO.OrderOnlineResponse;
import com.onestep.business_management.Entity.*;
import com.onestep.business_management.Utils.MapperService;
import com.onestep.business_management.Utils.StringToMapConverter;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

@Mapper
public interface OrderOnlineMapper {
    OrderOnlineMapper INSTANCE = Mappers.getMapper(OrderOnlineMapper.class);


    // Ánh xạ từ OrderOnline sang OrderOnlineResponse
    @Mapping(target = "orderId", source = "orderOnlineId")
    @Mapping(target = "orderDetails", source = "orderDetails", qualifiedByName = "mapDetailsToResponses")
    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "storeId", source = "store.storeId")
    OrderOnlineResponse toResponse(OrderOnline order);

    // Custom method to map List<OrderOnlineDetailRequest> to List<OrderOnlineDetail>
    @Named("mapDetailRequestsToEntities")
    default List<OrderOnlineDetail> mapDetailRequestsToEntities(List<OrderOnlineDetailRequest> detailRequests, @Context MapperService mapperService) {
        return detailRequests.stream().map(detailRequest -> {
            OrderOnlineDetail detail = new OrderOnlineDetail();
            detail.setQuantity(detailRequest.getQuantity());
            ProductDetail productDetail = mapperService.findProductDetailById(detailRequest.getProductDetailId());
            detail.setProductDetail(productDetail);
            detail.setPrice(productDetail.getPrice());
            return detail;
        }).toList();
    }

    // Custom method to map List<OrderOnlineDetail> to List<OrderOnlineDetailResponse>
    @Named("mapDetailsToResponses")
    default List<OrderOnlineDetailResponse> mapDetailsToResponses(List<OrderOnlineDetail> details) {
        return details.stream().map(detail -> {
            OrderOnlineDetailResponse response = new OrderOnlineDetailResponse();
            response.setOrderDetailId(detail.getOrderDetailId());
            response.setQuantity(detail.getQuantity());
            response.setPrice(detail.getPrice());
            ProductDetail productDetail = detail.getProductDetail();
            Product product = productDetail.getProduct();
            response.setProductName(product.getProductName());
            response.setTotalPrice(detail.getPrice() * detail.getQuantity());
            response.setImage(productDetail.getImage());
            response.setAttributes(StringToMapConverter.convertStringToMap(productDetail.getAttributes()));
            return response;
        }).toList();
    }

    // Mapping Store ID to Store entity using MapperService
    @Named("mapStoreIdToStore")
    default Store mapStoreIdToStore(UUID storeId, @Context MapperService mapperService) {
        return mapperService.findStoreById(storeId);
    }

    // Mapping User ID to User entity using MapperService
    @Named("mapUserIdToUser")
    default User mapUserIdToUser(UUID userId, @Context MapperService mapperService) {
        return mapperService.findUserById(userId);
    }
}
