package com.onestep.business_management.Service.ReviewSevice;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.onestep.business_management.Service.ProductService.ProductMapper;

@Mapper
public interface ReviewMapper {
    ReviewMapper INSTANCE =  Mappers.getMapper(ReviewMapper.class);
}
