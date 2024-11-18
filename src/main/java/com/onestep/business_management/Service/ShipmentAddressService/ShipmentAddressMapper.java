package com.onestep.business_management.Service.ShipmentAddressService;

import com.onestep.business_management.DTO.ShipmentAddressDTO.ShipmentAddressRequest;
import com.onestep.business_management.DTO.ShipmentAddressDTO.ShipmentAddressRespone;
import com.onestep.business_management.Entity.ShippingAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShipmentAddressMapper {
    ShippingAddress toEntity(ShipmentAddressRequest shipmentAddressRequest);

    ShipmentAddressRespone toResponse(ShippingAddress shippingAddress);
}
