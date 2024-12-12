package com.onestep.business_management.Service.ReviewSevice;

import java.util.List;
import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.onestep.business_management.DTO.ReviewDTO.ProductReviewResponse;
import com.onestep.business_management.DTO.ReviewDTO.ReviewRequest;
import com.onestep.business_management.DTO.ReviewDTO.ReviewResponse;
import com.onestep.business_management.Entity.Review;
import com.onestep.business_management.Utils.StringToMapConverter;

@Mapper
public interface ReviewMapper {
    ReviewMapper INSTANCE =  Mappers.getMapper(ReviewMapper.class);

    @Mapping(target = "productDetail.productDetailId", source = "productDetailId")
    @Mapping(target = "user.userId", source = "userId")
    Review toEntity(ReviewRequest reviewRequest);

    @Mapping(target = "productName", source = "productDetail.product.productName")
    @Mapping(target = "productDetailId", source = "productDetail.productDetailId")
    @Mapping(target = "productImage", source = "productDetail.image")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "imageUser", source = "user.image.fileName")
    @Mapping(target = "attributes", source = "productDetail.attributes", qualifiedByName = "stringToMap")
    ReviewResponse toResponse(Review review);

    @Named("stringToMap")
    default Map<String, String> stringToMap(String attributes) {
       return new StringToMapConverter().convertStringToMap(attributes);
    }
    
}
