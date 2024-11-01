package com.onestep.business_management.Controller;

import com.onestep.business_management.DTO.API.ApiResponse;
import com.onestep.business_management.DTO.AuthDTO.GoogleSignInRequest;
import com.onestep.business_management.DTO.AuthDTO.LoginRequest;
import com.onestep.business_management.DTO.AuthDTO.LoginResponse;
import com.onestep.business_management.DTO.ProductDTO.ProductResponse;
import com.onestep.business_management.Entity.User;
import com.onestep.business_management.Scurity.JWTService;
import com.onestep.business_management.Service.AuthService.AuthenticationService;
import com.onestep.business_management.Service.ImageService.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Map;

@Controller
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/login")
    public ResponseEntity<?> login_form(){
        return ResponseEntity.ok().body(Map.of("response","ok"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = authenticationService.login(loginRequest);
        ApiResponse<LoginResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),  // Status code 200
                "Login successfully",
                response,
                LocalDateTime.now()  // Current date
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/google-sign-in")
    public ResponseEntity<?> loginWithGoogle(@RequestBody GoogleSignInRequest loginRequest) {
        LoginResponse response = authenticationService.google_sign_in(loginRequest);
        ApiResponse<LoginResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),  // Status code 200
                "Login successfully",
                response,
                LocalDateTime.now()  // Current date
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }



    @PostMapping("/create_account")
    public ResponseEntity<?> create_account(@RequestBody User user){
        authenticationService.create_account(user);
        return ResponseEntity.ok().body(Map.of("user",user));
    }

    @PostMapping("/upload/image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(imageService.uploadImage(file));
    }
}
