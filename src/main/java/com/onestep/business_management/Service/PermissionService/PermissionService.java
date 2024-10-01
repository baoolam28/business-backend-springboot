package com.onestep.business_management.Service.PermissionService;

import com.onestep.business_management.DTO.PermissionDTO.PermissionRequest;
import com.onestep.business_management.DTO.PermissionDTO.PermissionResponse;
import com.onestep.business_management.Entity.Permission;
import com.onestep.business_management.Repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    public PermissionResponse createPermission(PermissionRequest permissionRequest) {
        Permission permission = PermissionMapper.INSTANCE.toEntity(permissionRequest);
        Permission savedPermission = permissionRepository.save(permission);
        return PermissionMapper.INSTANCE.toResponse(savedPermission);
    }

    public List<PermissionResponse> getAllPermissions() {
        return permissionRepository.findAll().stream()
                .map(PermissionMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    public PermissionResponse getPermissionById(Integer permissionId) {
        return permissionRepository.findById(permissionId)
                .map(PermissionMapper.INSTANCE::toResponse)
                .orElse(null);
    }

    public PermissionResponse updatePermission(Integer permissionId, PermissionRequest permissionRequest) {
        Permission permission = permissionRepository.findById(permissionId).orElse(null);
        if (permission != null) {
            permission.setPermissionName(permissionRequest.getPermissionName());
            Permission updatedPermission = permissionRepository.save(permission);
            return PermissionMapper.INSTANCE.toResponse(updatedPermission);
        }
        return null;
    }

    public boolean deletePermission(Integer permissionId) {
        if (permissionRepository.existsById(permissionId)) {
            permissionRepository.deleteById(permissionId);
            return true;
        }
        return false;
    }
}
