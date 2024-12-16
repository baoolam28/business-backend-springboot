package com.onestep.business_management.Service.ShipmentAddressService;

import com.onestep.business_management.DTO.ShipmentAddressDTO.ShipmentAddressRequest;
import com.onestep.business_management.DTO.ShipmentAddressDTO.ShipmentAddressRespone;
import com.onestep.business_management.Entity.ShippingAddress;
import com.onestep.business_management.Service.ShipmentService.ShipmentMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ShipmentAddressMapper {
    ShipmentAddressMapper INSTANCE = Mappers.getMapper(ShipmentAddressMapper.class);

    ShippingAddress toEntity(ShipmentAddressRequest shipmentAddressRequest);

    ShipmentAddressRespone toResponse(ShippingAddress shippingAddress);
}
