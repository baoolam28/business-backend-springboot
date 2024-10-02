package com.onestep.business_management.Service.AuthService;

import com.onestep.business_management.DTO.AuthDTO.BuyerRegistrationRequest;
import com.onestep.business_management.DTO.AuthDTO.BuyerRegistrationResponse;
import com.onestep.business_management.Entity.Role;
import com.onestep.business_management.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User buyerToEntity(BuyerRegistrationRequest userRequest);

    @Mapping(target = "roles", source = "roles")
    BuyerRegistrationResponse buyerToResponse(User user);

    default Set<String> map(Set<Role> roles) {
        if (roles == null) return null;
        return roles.stream()
                .map(Role::getRoleName) // Assuming Role has a getRoleName method
                .collect(Collectors.toSet());
    }

}
