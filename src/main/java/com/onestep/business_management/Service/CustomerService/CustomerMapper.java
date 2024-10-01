package com.onestep.business_management.Service.CustomerService;

import com.onestep.business_management.DTO.CustomerDTO.CustomerRequest;
import com.onestep.business_management.DTO.CustomerDTO.CustomerResponse;
import com.onestep.business_management.Entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "customerId", ignore = true) // Ignoring ID for create operations
    Customer toEntity(CustomerRequest customerRequest);

    CustomerResponse toResponse(Customer customer);
}
