package com.onestep.business_management.Service.ShipmentAddressService;

import com.onestep.business_management.DTO.ShipmentAddressDTO.ShipmentAddressRequest;
import com.onestep.business_management.DTO.ShipmentAddressDTO.ShipmentAddressRespone;
import com.onestep.business_management.Entity.ShippingAddress;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShipmentAddressMapper {
    ShipmentAddressMapper INSTANCE = Mappers.getMapper(ShipmentAddressMapper.class);

    // Ánh xạ từ DTO request sang entity
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "addressLine", target = "address")
    ShippingAddress toEntity(ShipmentAddressRequest request);

    // Ánh xạ từ entity sang DTO response
    @Mapping(source = "address", target = "addressLine")
    ShipmentAddressRespone toResponse(ShippingAddress entity);
}
