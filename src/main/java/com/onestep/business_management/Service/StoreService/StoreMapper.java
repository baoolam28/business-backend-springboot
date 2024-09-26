package com.onestep.business_management.Service.StoreService;

import com.onestep.business_management.DTO.StoreRequest;
import com.onestep.business_management.DTO.StoreResponse;
import com.onestep.business_management.Entity.Store;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StoreMapper {
    StoreMapper INSTANCE = Mappers.getMapper(StoreMapper.class);

    @Mapping(target = "storeManager.userId", source = "storeManagerId")
    Store toEntity(StoreRequest storeRequest);

    @Mapping(target = "storeManagerId", source = "storeManager.userId")
    StoreResponse toResponse(Store store);
}
