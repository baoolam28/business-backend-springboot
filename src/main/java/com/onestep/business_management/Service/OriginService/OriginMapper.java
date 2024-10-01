package com.onestep.business_management.Service.OriginService;

import com.onestep.business_management.DTO.OriginDTO.OriginRequest;
import com.onestep.business_management.DTO.OriginDTO.OriginResponse;
import com.onestep.business_management.Entity.Origin;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OriginMapper {
    OriginMapper INSTANCE = Mappers.getMapper(OriginMapper.class);

    Origin toEntity(OriginRequest originRequest);

    OriginResponse toResponse(Origin origin);
}
