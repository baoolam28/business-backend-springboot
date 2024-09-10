package com.onestep.business_management.Service.SupplierService;
import com.onestep.business_management.DTO.SupplierRequest;
import com.onestep.business_management.DTO.SupplierResponse;
import com.onestep.business_management.Entity.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper
public interface SupplierMapper {
    SupplierMapper INSTANCE = Mappers.getMapper(SupplierMapper.class);

    // Ánh xạ từ SupplierRequest sang Supplier
    @Mapping(source = "supplierName", target = "supplierName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "fax", target = "fax")
    @Mapping(source = "address", target = "address")
    Supplier toEntity(SupplierRequest request);

    // Ánh xạ từ Supplier sang SupplierResponse
    @Mapping(source = "supplierName", target = "supplierName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "fax", target = "fax")
    @Mapping(source = "address", target = "address")
    SupplierResponse toResponse(Supplier supplier);
}
