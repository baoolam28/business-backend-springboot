package com.onestep.business_management.Service.ShipmentAddressService;

import com.onestep.business_management.DTO.ShipmentAddressDTO.ShipmentAddressRequest;
import com.onestep.business_management.DTO.ShipmentAddressDTO.ShipmentAddressRespone;
import com.onestep.business_management.Entity.ShippingAddress;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ShipmentAddressMapper {
    ShipmentAddressMapper INSTANCE = Mappers.getMapper(ShipmentAddressMapper.class);

    ShippingAddress toEntity(ShipmentAddressRequest request);

    ShipmentAddressRespone toResponse(ShippingAddress shippingAddress);
}
