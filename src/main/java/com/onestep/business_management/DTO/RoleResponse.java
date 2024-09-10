package com.onestep.business_management.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {
    private Integer roleId;
    private String roleName;
    private Set<Integer> permissionIds;
}
