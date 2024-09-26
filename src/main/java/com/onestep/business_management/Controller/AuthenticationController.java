package com.onestep.business_management.Controller;

import com.onestep.business_management.DTO.LoginRequest;
import com.onestep.business_management.DTO.LoginResponse;
import com.onestep.business_management.Entity.User;
import com.onestep.business_management.Scurity.JWTService;
import com.onestep.business_management.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationProvider authenticationProvider;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JWTService jwtService;
    @Autowired
    AuthenticationService authenticationService;


    @GetMapping("/login")
    public ResponseEntity<?> login_form(){
        return ResponseEntity.ok().body(Map.of("response","ok"));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest.toString());
        authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        var userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        var jwtToken = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(jwtToken));
    }

    @PostMapping("/create_account")
    public ResponseEntity<?> create_account(@RequestBody User user){
        authenticationService.create_account(user);
        return ResponseEntity.ok().body(Map.of("user",user));
    }
}
