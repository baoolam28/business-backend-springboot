package com.onestep.business_management.Service.RoleService;

import com.onestep.business_management.DTO.RoleRequest;
import com.onestep.business_management.DTO.RoleResponse;
import com.onestep.business_management.Entity.Permission;
import com.onestep.business_management.Entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mappings({
            @Mapping(target = "permissions", source = "permissionIds", qualifiedByName = "mapIdsToPermissions")
    })
    Role toEntity(RoleRequest roleRequest);

    @Mappings({
            @Mapping(target = "permissionIds", source = "permissions", qualifiedByName = "mapPermissionsToIds")
    })
    RoleResponse toResponse(Role role);

    // Custom method to map Set<Integer> to Set<Permission>
    @Named("mapIdsToPermissions")
    default Set<Permission> mapIdsToPermissions(Set<Integer> ids) {
        // Assuming you have a way to fetch permissions by IDs (perhaps from a service or repository)
        // You need to replace the following code with your actual implementation to fetch permissions by IDs
        return ids.stream()
                .map(id -> {
                    Permission permission = new Permission();
                    permission.setPermissionId(id);
                    return permission;
                })
                .collect(Collectors.toSet());
    }

    // Custom method to map Set<Permission> to Set<Integer>
    @Named("mapPermissionsToIds")
    default Set<Integer> mapPermissionsToIds(Set<Permission> permissions) {
        return permissions.stream()
                .map(Permission::getPermissionId)
                .collect(Collectors.toSet());
    }
}
