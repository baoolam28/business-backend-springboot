package com.onestep.business_management.Service.CustomerService;

import com.onestep.business_management.DTO.CustomerDTO.CustomerRequest;
import com.onestep.business_management.DTO.CustomerDTO.CustomerResponse;
import com.onestep.business_management.Entity.Customer;
import com.onestep.business_management.Entity.Store;
import com.onestep.business_management.Utils.MapperService;

import org.mapstruct.factory.Mappers;

import java.util.UUID;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "customerId", ignore = true) // Ignoring ID for create operations
    @Mapping(target = "store", source = "storeId", qualifiedByName = "mapStoreIdToStore")
    Customer toEntity(CustomerRequest customerRequest);

    @Named("mapStoreIdToStore") // Phương thức ánh xạ storeId thành Store
    default Store mapStoreIdToStore(UUID storeId) {
        if (storeId == null) {
            return null; // Trả về null nếu storeId không có
        }
        Store store = new Store();
        store.setStoreId(storeId); // Giả sử Store có phương thức setStoreId(UUID storeId)
        return store;
    }

    CustomerResponse toResponse(Customer customer);
}
