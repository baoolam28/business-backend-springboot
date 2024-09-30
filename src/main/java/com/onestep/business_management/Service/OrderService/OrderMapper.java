package com.onestep.business_management.Service.OrderService;

import com.onestep.business_management.DTO.OrderDTO.OrderDetailRequest;
import com.onestep.business_management.DTO.OrderDTO.OrderDetailResponse;
import com.onestep.business_management.DTO.OrderDTO.OrderRequest;
import com.onestep.business_management.DTO.OrderDTO.OrderResponse;
import com.onestep.business_management.Entity.OrderOffline;
import com.onestep.business_management.Entity.OrderOfflineDetail;
import com.onestep.business_management.Entity.Product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mappings({
            @Mapping(target = "orderDetails", source = "orderDetails", qualifiedByName = "mapOrderDetailRequestsToEntities")
    })
    OrderOffline toEntity(OrderRequest orderRequest);

    @Mappings({
            @Mapping(target = "orderDetails", source = "orderDetails", qualifiedByName = "mapOrderDetailsToResponses")
    })
    OrderResponse toResponse(OrderOffline order);

    // Custom method to map List<OrderDetailRequest> to List<OrderDetail>
    @Named("mapOrderDetailRequestsToEntities")
    default List<OrderOfflineDetail> mapOrderDetailRequestsToEntities(List<OrderDetailRequest> requests) {
        return requests.stream().map(request -> {
            OrderOfflineDetail detail = new OrderOfflineDetail();
            detail.setQuantity(request.getQuantity());
            detail.setPrice(request.getPrice());
            // Assuming you have a way to fetch product by barcode (perhaps from a service or repository)
            // You need to replace the following code with your actual implementation to fetch product by barcode
            // detail.setProduct(productService.findByBarcode(request.getProductBarcode()));
            return detail;
        }).toList();
    }

    // Custom method to map List<OrderDetail> to List<OrderDetailResponse>
    @Named("mapOrderDetailsToResponses")
    default List<OrderDetailResponse> mapOrderDetailsToResponses(List<OrderOfflineDetail> details) {
        return details.stream().map(detail -> {
            OrderDetailResponse response = new OrderDetailResponse();
            response.setQuantity(detail.getQuantity());
            response.setPrice(detail.getPrice());
            Product product = detail.getProduct();
            // response.setProduct(detail.getProduct());
            // Assuming you have a way to get product details (perhaps from a service or repository)
            // You need to replace the following code with your actual implementation to get product details
            // response.setProductBarcode(detail.getProduct().getBarcode());
            return response;
        }).toList();
    }
}
