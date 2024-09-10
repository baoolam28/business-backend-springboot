package com.onestep.business_management.Service.DocumentService;

import com.onestep.business_management.DTO.DocumentRequest;
import com.onestep.business_management.DTO.DocumentResponse;
import com.onestep.business_management.DTO.DocumentDetailRequest;
import com.onestep.business_management.DTO.DocumentDetailResponse;
import com.onestep.business_management.Entity.Document;
import com.onestep.business_management.Entity.DocumentDetail;
import com.onestep.business_management.Entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DocumentMapper {
    DocumentMapper INSTANCE = Mappers.getMapper(DocumentMapper.class);

    @Mapping(target = "documentDetails", source = "documentDetails")
    Document toEntity(DocumentRequest documentRequest);

    @Mapping(target = "documentDetails", source = "documentDetails")
    DocumentResponse toResponse(Document document);

    DocumentDetail toEntity(DocumentDetailRequest documentDetailRequest);

    @Mapping(source = "product.barcode", target = "productBarcode")
    DocumentDetailResponse toResponse(DocumentDetail documentDetail);

    List<DocumentDetailResponse> toDetailResponses(List<DocumentDetail> documentDetails);
}
