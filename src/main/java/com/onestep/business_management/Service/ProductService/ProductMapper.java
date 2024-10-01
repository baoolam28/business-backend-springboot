package com.onestep.business_management.Service.ProductService;

import com.onestep.business_management.DTO.ProductDTO.ProductRequest;
import com.onestep.business_management.DTO.ProductDTO.ProductResponse;
import com.onestep.business_management.Entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mappings({
            @Mapping(target = "category.categoryId", source = "categoryId"),
            @Mapping(target = "supplier.supplierId", source = "supplierId"),
            @Mapping(target = "origin.originId", source = "originId")
    })
    Product toEntity(ProductRequest productRequest);

    @Mappings({
            @Mapping(target = "categoryId", source = "category.categoryId"),
            @Mapping(target = "categoryName", source = "category.categoryName"),
            @Mapping(target = "supplierId", source = "supplier.supplierId"),
            @Mapping(target = "supplierName", source = "supplier.supplierName"),
            @Mapping(target = "originId", source = "origin.originId"),
            @Mapping(target = "originName", source = "origin.originName")
    })
    ProductResponse toResponse(Product product);



}
