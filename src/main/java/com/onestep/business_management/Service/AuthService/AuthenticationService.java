package com.onestep.business_management.Service.AuthService;

import com.onestep.business_management.DTO.AuthDTO.*;
import com.onestep.business_management.Entity.Image;
import com.onestep.business_management.Entity.Role;
import com.onestep.business_management.Entity.User;
import com.onestep.business_management.Exeption.InvalidAccountExeption;
import com.onestep.business_management.Exeption.ResourceNotFoundException;
import com.onestep.business_management.Repository.RoleRepository;
import com.onestep.business_management.Repository.UserRepository;
import com.onestep.business_management.Scurity.JWTService;
import com.onestep.business_management.Service.StoreService.StoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    @Autowired
    AuthenticationProvider authenticationProvider;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JWTService jwtService;

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
            throw new IllegalArgumentException("Account already exists");
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


    public LoginResponse login(LoginRequest loginRequest){

        System.out.println(loginRequest.toString());
        String userName = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        var authProvider = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(userName, password)
        );

        if(authProvider == null){
            throw new InvalidAccountExeption("Invalid username or password");
        }

        var userDetails = userDetailsService.loadUserByUsername(userName);



        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );

        String fullName = user.getFullName();
        Set<Role> roles = user.getRoles();
        String roleName = roles.toString();

        boolean isActive = user.isDisabled();

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getUserId());
        userInfo.setUsername(userName);
        userInfo.setRole(roleName);
        userInfo.setFullName(fullName);
        Image image = user.getImage();
        if(image != null){
            userInfo.setImage(image.getFileName());
        }
        userInfo.setActive(isActive);

        String  accessToken = jwtService.generateToken(userDetails);

        return  new LoginResponse(accessToken,userInfo);
    }


    public LoginResponse google_sign_in(GoogleSignInRequest request){
        User existUser = userRepository.findByUsername(request.getEmail()).orElse(null);

        if(existUser == null){
            User newUser = new User();
            newUser.setUsername(request.getEmail());
            newUser.setPassword(request.getAt_hash());
            newUser.setFullName(request.getName());
            newUser.setDisabled(false);
            int roleBuyer = roleCode.get("ROLE_BUYER");
            Role newRole = roleRepository.findById(roleBuyer).orElseThrow(
                    () -> new RuntimeException("Buyer role not found!")
            );
            Set<Role> roles = new HashSet<>();
            roles.add(newRole);
            newUser.setRoles(roles);

            ;
            return  convertToResponse(userRepository.save(newUser));
        }


        return convertToResponse(existUser);
    }

    private LoginResponse convertToResponse(User newUser){

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(newUser.getUserId());
        userInfo.setUsername(newUser.getUsername());
        userInfo.setRole(newUser.getRoles().toString());
        userInfo.setFullName(newUser.getFullName());
        userInfo.setActive(newUser.isDisabled());

        String  accessToken = jwtService.generateToken(newUser);

        return new LoginResponse(accessToken,userInfo);
    }
}


