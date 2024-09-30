package com.onestep.business_management.Service.CategoryService;

import com.onestep.business_management.DTO.CategoryDTO.CategoryRequest;
import com.onestep.business_management.DTO.CategoryDTO.CategoryResponse;
import com.onestep.business_management.Entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category toEntity(CategoryRequest categoryRequest);

    CategoryResponse toResponse(Category category);
}
