package com.onestep.business_management.Service.StoreService;

import com.onestep.business_management.DTO.StoreDTO.StoreRequest;
import com.onestep.business_management.DTO.StoreDTO.StoreResponse;
import com.onestep.business_management.Entity.Store;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StoreMapper {
    StoreMapper INSTANCE = Mappers.getMapper(StoreMapper.class);

    @Mapping(target = "storeManager.userId", source = "storeManager")
    @Mapping(target = "storeDescription", source = "storeDescription")
    @Mapping(target = "storeEmail", source = "storeEmail")
    @Mapping(target = "storeBankAccount", source = "storeBankAccount")
    @Mapping(target = "pickupAddress", source = "pickupAddress")
    @Mapping(target = "storeTaxCode", source = "storeTaxCode")
    @Mapping(target = "managerName", source = "managerName")
    @Mapping(target = "wardCode", source = "wardCode")
    
    Store toEntity(StoreRequest storeRequest);
    @Mapping(target = "storeManager", source = "storeManager.userId")
    StoreResponse toResponse(Store store);
    

}
