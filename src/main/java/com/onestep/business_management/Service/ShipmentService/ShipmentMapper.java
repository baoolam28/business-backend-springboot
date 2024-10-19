package com.onestep.business_management.Service.ShipmentService;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.onestep.business_management.DTO.OrderDTO.OrderOnlineDetailRequest;
import com.onestep.business_management.DTO.OrderDTO.OrderOnlineDetailResponse;
import com.onestep.business_management.DTO.ShippingDTO.ShipmentRequest;
import com.onestep.business_management.DTO.ShippingDTO.ShipmentResponse;
import com.onestep.business_management.Entity.OrderOnline;
import com.onestep.business_management.Entity.OrderOnlineDetail;
import com.onestep.business_management.Entity.Product;
import com.onestep.business_management.Entity.ProductDetail;
import com.onestep.business_management.Entity.Shipment;
import com.onestep.business_management.Entity.ShippingAddress;
import com.onestep.business_management.Utils.MapperService;
import com.onestep.business_management.Utils.StringToMapConverter;

@Mapper(componentModel = "spring")
public interface ShipmentMapper {
    ShipmentMapper INSTANCE = Mappers.getMapper(ShipmentMapper.class);

    // Ánh xạ từ Shipment sang ShipmentResponse
    @Mapping(target = "shipmentId", source = "shipmentId")
    @Mapping(target = "userId", source = "orderOnline.user.userId")
    @Mapping(target = "addressId", source = "shippingAddress.addressId")
    @Mapping(target = "storeId", source = "orderOnline.store.storeId")
    @Mapping(target = "storeName", source = "orderOnline.store.storeName")
    @Mapping(target = "shippingStatus", source = "shippingStatus")
    @Mapping(target = "orderOnlineDetails", source = "orderOnline.orderDetails", qualifiedByName = "mapOrderDetailsToResponses")
    ShipmentResponse toResponse(Shipment shipment);


    @Named("mapOrderDetailsToResponses")
    default List<OrderOnlineDetailResponse> mapOrderDetailsToResponses(List<OrderOnlineDetail> details) {
        return details.stream().map(detail -> {
            OrderOnlineDetailResponse response = new OrderOnlineDetailResponse();
            response.setOrderDetailId(detail.getOrderDetailId());
            response.setProductDetailId(detail.getProductDetail().getProductDetailId());
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

    @Mapping(target = "shippingAddress", source = "addressId", qualifiedByName = "mapAddressIdToAddress")
    @Mapping(target = "shippingStatus", source = "shippingStatus")
    Shipment toEntity(ShipmentRequest request, @Context MapperService mapperService);

    @Named("mapAddressIdToAddress")
    default ShippingAddress mapAddressIdToAddress(Integer addressId, @Context MapperService mapperService) {
        return mapperService.findShippingAddressById(addressId);
    }
}
