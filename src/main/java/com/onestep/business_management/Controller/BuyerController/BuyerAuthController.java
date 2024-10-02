package com.onestep.business_management.Controller.BuyerController;

import com.onestep.business_management.DTO.AuthDTO.BuyerRegistrationRequest;
import com.onestep.business_management.DTO.AuthDTO.BuyerRegistrationResponse;
import com.onestep.business_management.DTO.AuthDTO.LoginRequest;
import com.onestep.business_management.DTO.AuthDTO.LoginResponse;
import com.onestep.business_management.Service.AuthService.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/buyer/auth")
public class BuyerAuthController {

    @Autowired
    private AuthenticationService authService;

    @PostMapping
    public ResponseEntity<BuyerRegistrationResponse> buyerRegister(@RequestBody BuyerRegistrationRequest registerRequest) {
        try {
            BuyerRegistrationResponse response = authService.buyer_register(registerRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
