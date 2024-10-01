package com.onestep.business_management.Service.ReviewSevice;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.onestep.business_management.DTO.ReviewRequest;
import com.onestep.business_management.DTO.ReviewResponse;
import com.onestep.business_management.Entity.Review;


@Mapper
public interface ReviewMapper {
    ReviewMapper INSTANCE =  Mappers.getMapper(ReviewMapper.class);

    @Mappings({
        @Mapping(target = "product.productId", source = "productId"),
        @Mapping(target = "user.userId", source = "userId")
    })
    Review toEntity(ReviewRequest reviewRequest);

    @Mappings({
        @Mapping(target = "productName", source = "product.productName"),
        @Mapping(target = "fullName", source = "user.fullName")
    })
    ReviewResponse toResponse(Review review);
}
