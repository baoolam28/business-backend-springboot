package com.onestep.business_management.Service.AuthService;

import com.onestep.business_management.DTO.AuthDTO.BuyerRegistrationRequest;
import com.onestep.business_management.DTO.AuthDTO.BuyerRegistrationResponse;
import com.onestep.business_management.Entity.Role;
import com.onestep.business_management.Entity.User;
import com.onestep.business_management.Repository.RoleRepository;
import com.onestep.business_management.Repository.UserRepository;
import com.onestep.business_management.Service.StoreService.StoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.*;

@Service
public class AuthenticationService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    private Map<String, Integer> roleCode = new HashMap<>() {{
        put("ROLE_ADMIN", 1);
        put("ROLE_SELLER",  2);
        put("ROLE_BUYER", 3);
        put("ROLE_STAFF", 4);
    }};

    public void create_account(User user){

        User findUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        System.out.println(findUser);
        if (findUser != null) {
            throw new RuntimeException("Username already taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public BuyerRegistrationResponse buyer_register(BuyerRegistrationRequest request){
        User exitsUser = userRepository.findByUsername(request.getUsername()).orElse(null);

        if(exitsUser != null){
            throw new RuntimeException("Account already exists");
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());

        // Encoding password
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        newUser.setPassword(encodedPassword);

        // Set role buyer to user
        int roleBuyer = roleCode.get("ROLE_BUYER");
        Role newRole = roleRepository.findById(roleBuyer).orElseThrow(
                () -> new RuntimeException("Buyer role not found!")
        );

        System.out.println(newRole);

        // Corrected: Create a set and add the role to it
        Set<Role> roles = new HashSet<>();
        roles.add(newRole);
        newUser.setRoles(roles);

        // Setting other fields
        newUser.setFullName(request.getFullName());
        newUser.setPhoneNumber(request.getPhoneNumber());

        System.out.println("new User: "+ newUser.toString());
        try {
            User savedUser = userRepository.save(newUser);
            return UserMapper.INSTANCE.buyerToResponse(savedUser);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException("An error occurred while registering the buyer.");
        }
    }
}
