package com.onestep.business_management.Service.OrderService;

import com.onestep.business_management.DTO.OrderDTO.OrderDetailRequest;
import com.onestep.business_management.DTO.OrderDTO.OrderDetailResponse;
import com.onestep.business_management.DTO.OrderDTO.OrderRequest;
import com.onestep.business_management.DTO.OrderDTO.OrderResponse;
import com.onestep.business_management.Entity.*;

import com.onestep.business_management.Utils.MapperService;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    // Ánh xạ từ OrderRequest sang OrderOffline
    @Mapping(target = "store", source = "storeId", qualifiedByName = "mapStoreIdToStore")
    @Mapping(target = "customer", source = "customerId", qualifiedByName = "mapCustomerIdToCustomer")
    OrderOffline toEntity(OrderRequest orderRequest, @Context MapperService mapperService);

    // Ánh xạ từ OrderOffline sang OrderResponse
    @Mapping(target = "orderId", source = "orderOfflineId")
    @Mapping(target = "orderDetails", source = "orderDetails", qualifiedByName = "mapOrderDetailsToResponses")
    @Mapping(target = "customerId", source = "customer.customerId")
    @Mapping(target = "storeId" , source = "store.storeId")
    OrderResponse toResponse(OrderOffline order);


    // Custom method to map List<OrderOfflineDetail> to List<OrderDetailResponse>
    @Named("mapOrderDetailsToResponses")
    default List<OrderDetailResponse> mapOrderDetailsToResponses(List<OrderOfflineDetail> details) {
        return details.stream().map(detail -> {
            OrderDetailResponse response = new OrderDetailResponse();
            response.setOrderDetailId(detail.getOrderDetailId());
            response.setQuantity(detail.getQuantity());
            response.setPrice(detail.getPrice());
            response.setBarcode(detail.getBarcode());
            return response;
        }).toList();
    }

    // Mapping Store ID to Store entity using MapperService
    @Named("mapStoreIdToStore")
    default Store mapStoreIdToStore(UUID storeId, @Context MapperService mapperService) {
        return mapperService.findStoreById(storeId);
    }

    // Mapping Customer ID to Customer entity using MapperService
    @Named("mapCustomerIdToCustomer")
    default Customer mapCustomerIdToCustomer(Integer customerId, @Context MapperService mapperService) {
        return mapperService.findCustomerById(customerId);
    }
}

