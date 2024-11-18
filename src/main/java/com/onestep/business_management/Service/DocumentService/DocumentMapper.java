package com.onestep.business_management.Service.DocumentService;

import com.onestep.business_management.DTO.DocumentDTO.DocumentDetailRequest;
import com.onestep.business_management.DTO.DocumentDTO.DocumentDetailResponse;
import com.onestep.business_management.DTO.DocumentDTO.DocumentRequest;
import com.onestep.business_management.DTO.DocumentDTO.DocumentResponse;
import com.onestep.business_management.Entity.Document;
import com.onestep.business_management.Entity.DocumentDetail;
import com.onestep.business_management.Entity.Product;
import com.onestep.business_management.Entity.User;
import com.onestep.business_management.Utils.MapperService;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

@Mapper
public interface DocumentMapper {
    DocumentMapper INSTANCE = Mappers.getMapper(DocumentMapper.class);

    @Mapping(target = "documentDetails", source = "documentDetails")
    Document toEntity(DocumentRequest documentRequest);

    @Mapping(target = "documentDetails", source = "documentDetails")
    @Mapping(target = "createdBy", source = "createdBy", qualifiedByName = "userIdToName")
    DocumentResponse toResponse(Document document, @Context MapperService mapperService);

    DocumentDetail toEntity(DocumentDetailRequest documentDetailRequest);

    @Mapping(source = "product.barcode", target = "productBarcode")
    @Mapping(source = "product.productName", target = "productName")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = ".", target = "totalPrice", qualifiedByName = "calculateTotalPrice")
    DocumentDetailResponse toResponse(DocumentDetail documentDetail);

    @Named("calculateTotalPrice")
    default double calculateTotalPrice(DocumentDetail documentDetail) {
        return documentDetail.getPrice() * documentDetail.getQuantity();
    }

    @Named("userIdToName")
    default String convertUserIdToName(UUID createBy, @Context MapperService mapperService){

        if(createBy == null) return "";

        User user = mapperService.findUserById(createBy);
        return user.getFullName();
    }

    List<DocumentDetailResponse> toDetailResponses(List<DocumentDetail> documentDetails);
}
