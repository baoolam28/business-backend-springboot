package com.onestep.business_management.Service.InventoryService;

import com.onestep.business_management.DTO.InventoryRequest;
import com.onestep.business_management.DTO.InventoryResponse;
import com.onestep.business_management.Entity.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InventoryMapper {
    InventoryMapper INSTANCE = Mappers.getMapper(InventoryMapper.class);

    @Mapping(target = "barcode", source = "barcode")
    Inventory toEntity(InventoryRequest inventoryRequest);

    @Mapping(target = "barcode", source = "barcode")
    InventoryResponse toResponse(Inventory inventory);
}
