package com.onestep.business_management.Service.RoleService;

import com.onestep.business_management.DTO.RoleRequest;
import com.onestep.business_management.DTO.RoleResponse;
import com.onestep.business_management.Entity.Role;
import com.onestep.business_management.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public RoleResponse createRole(RoleRequest roleRequest) {
        Role role = RoleMapper.INSTANCE.toEntity(roleRequest);
        Role savedRole = roleRepository.save(role);
        return RoleMapper.INSTANCE.toResponse(savedRole);
    }

    public RoleResponse getRoleById(Integer roleId) {
        Role role = roleRepository.findById(roleId).orElse(null);
        if (role != null) {
            return RoleMapper.INSTANCE.toResponse(role);
        }
        return null;
    }

    public Set<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(RoleMapper.INSTANCE::toResponse)
                .collect(Collectors.toSet());
    }

    public RoleResponse updateRole(Integer roleId, RoleRequest roleRequest) {
        Role role = roleRepository.findById(roleId).orElse(null);
        if (role != null) {
            role.setRoleName(roleRequest.getRoleName());
            // update permissions if necessary
            Role updatedRole = roleRepository.save(role);
            return RoleMapper.INSTANCE.toResponse(updatedRole);
        }
        return null;
    }

    public boolean deleteRole(Integer roleId) {
        if (roleRepository.existsById(roleId)) {
            roleRepository.deleteById(roleId);
            return true;
        }
        return false;
    }
}
