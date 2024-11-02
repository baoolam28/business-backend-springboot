package com.onestep.business_management.Service.ShipmentService;

import java.util.List;
import java.util.ArrayList;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.onestep.business_management.DTO.OrderDTO.OrderOnlineDetailRequest;
import com.onestep.business_management.DTO.OrderDTO.OrderOnlineDetailResponse;
import com.onestep.business_management.DTO.ShippingDTO.ShipmentRequest;
import com.onestep.business_management.DTO.ShippingDTO.ShipmentResponse;
import com.onestep.business_management.DTO.ShippingDTO.StatusHistoryResponse;
import com.onestep.business_management.Entity.OrderOnline;
import com.onestep.business_management.Entity.OrderOnlineDetail;
import com.onestep.business_management.Entity.Product;
import com.onestep.business_management.Entity.ProductDetail;
import com.onestep.business_management.Entity.Shipment;
import com.onestep.business_management.Entity.ShippingAddress;
import com.onestep.business_management.Entity.Shipment.ShippingStatus;
import com.onestep.business_management.Utils.MapperService;
import com.onestep.business_management.Utils.StringToMapConverter;

@Mapper(componentModel = "spring")
public interface ShipmentMapper {
    ShipmentMapper INSTANCE = Mappers.getMapper(ShipmentMapper.class);

    // Ánh xạ từ Shipment sang ShipmentResponse
    @Mapping(target = "shipmentId", source = "shipmentId")
    @Mapping(target = "userId", source = "orderOnline.user.userId")
    @Mapping(target = "addressId", source = "shippingAddress.addressId")
    @Mapping(target = "orderDate", source = "orderOnline.orderDate")
    @Mapping(target = "storeId", source = "orderOnline.store.storeId")
    @Mapping(target = "storeName", source = "orderOnline.store.storeName")
    @Mapping(target = "shippingStatus", source = "shippingStatus")
    @Mapping(target = "orderOnlineDetails", source = "orderOnline.orderDetails", qualifiedByName = "mapOrderDetailsToResponses")
    @Mapping(target = "createAt", source = "createAt")
    @Mapping(target = "shippedDate", source = "shippedDate")
    @Mapping(target = "deliveredDate", source = "deliveredDate")
    @Mapping(target = "updateAt", source = "updateAt")
    @Mapping(target = "shippingNote", source = "shippingNote")
    @Mapping(target = "statusHistory", source = ".", qualifiedByName = "mapStatusHistoryResponse")
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

    @Named("mapStatusHistoryResponse")
    default List<StatusHistoryResponse> mapStatusHistoryResponse(Shipment shipment){
        List<StatusHistoryResponse> historyList = new ArrayList<>();

        historyList.add(new StatusHistoryResponse(ShippingStatus.CHO_XAC_NHAN, shipment.getOrderOnline().getOrderDate()));

        if(shipment.getShippingStatus().getValue() >= ShippingStatus.DA_XAC_NHAN.getValue()){
            historyList.add(new StatusHistoryResponse(ShippingStatus.DA_XAC_NHAN, shipment.getCreateAt()));
        }

        if(shipment.getShippingStatus().getValue() >= ShippingStatus.DANG_GIAO.getValue()){
            historyList.add(new StatusHistoryResponse(ShippingStatus.DANG_GIAO, shipment.getShippedDate()));
        }

        if(shipment.getShippingStatus().getValue() >= ShippingStatus.GIAO_HANG_THANH_CONG.getValue()){
            historyList.add(new StatusHistoryResponse(ShippingStatus.GIAO_HANG_THANH_CONG, shipment.getDeliveredDate()));
        }

        if(shipment.getShippingStatus().getValue() >= ShippingStatus.DA_HUY_DON.getValue()){
            historyList.add(new StatusHistoryResponse(ShippingStatus.DA_HUY_DON, shipment.getCanceledDate()));
        }

        if(shipment.getShippingStatus().getValue() == ShippingStatus.GIAO_HANG_THAT_BAI.getValue()){
            historyList.add(new StatusHistoryResponse(ShippingStatus.GIAO_HANG_THAT_BAI, shipment.getFailedDeliveryDate()));
        }

        return historyList;
    }

    @Mapping(target = "shippingAddress", source = "addressId", qualifiedByName = "mapAddressIdToAddress")
    @Mapping(target = "shippingStatus", source = "shippingStatus")
    Shipment toEntity(ShipmentRequest request, @Context MapperService mapperService);

    @Named("mapAddressIdToAddress")
    default ShippingAddress mapAddressIdToAddress(Integer addressId, @Context MapperService mapperService) {
        return mapperService.findShippingAddressById(addressId);
    }
}
