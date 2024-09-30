package com.onestep.business_management.Service.PermissionService;

import com.onestep.business_management.DTO.PermissionDTO.PermissionRequest;
import com.onestep.business_management.DTO.PermissionDTO.PermissionResponse;
import com.onestep.business_management.Entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PermissionMapper {
    PermissionMapper INSTANCE = Mappers.getMapper(PermissionMapper.class);

    Permission toEntity(PermissionRequest permissionRequest);

    PermissionResponse toResponse(Permission permission);
}
