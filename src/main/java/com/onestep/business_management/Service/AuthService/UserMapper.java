package com.onestep.business_management.Service.AuthService;

import com.onestep.business_management.DTO.AuthDTO.BuyerRegistrationRequest;
import com.onestep.business_management.DTO.AuthDTO.BuyerRegistrationResponse;

import com.onestep.business_management.DTO.AuthDTO.StaffRegistrationRequest;
import com.onestep.business_management.DTO.AuthDTO.StaffResgitrationResponse;

import com.onestep.business_management.DTO.AuthDTO.UserBuyerInfoRequest;
import com.onestep.business_management.DTO.AuthDTO.UserBuyerInfoRespont;
import com.onestep.business_management.DTO.AuthDTO.UserchangesPassRequest;

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

    default UserBuyerInfoRespont userToBuyerInfoRespont(User user) {
        if (user == null) {
            return null;
        }

        UserBuyerInfoRespont response = new UserBuyerInfoRespont();

        // Set basic user fields
        response.setUserId(user.getUserId());
        response.setUsername(user.getUsername());
        response.setFullName(user.getFullName());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setEmail(user.getEmail());

        // Set image name if image is not null
        if (user.getImage() != null) {
            response.setImageName(user.getImage().getFileName());
        }

        return response;    
    }

    default Set<String> map(Set<Role> roles) {
        if (roles == null)
            return null;
        return roles.stream()
                .map(Role::getRoleName) // Assuming Role has a getRoleName method
                .collect(Collectors.toSet());
    }
    User staffToEntity(StaffRegistrationRequest staffRequest);

    @Mapping(target = "roles", source = "roles")
    StaffResgitrationResponse staffToResponse(User user);

    @Mapping(target = "fullName", source = "fullName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    User buyerInfoRequestToEntity(UserBuyerInfoRequest userBuyerInfoRequest);

}
