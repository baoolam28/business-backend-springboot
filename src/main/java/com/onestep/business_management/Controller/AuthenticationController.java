package com.onestep.business_management.Controller;

import com.onestep.business_management.DTO.API.ApiResponse;
import com.onestep.business_management.DTO.AuthDTO.GoogleSignInRequest;
import com.onestep.business_management.DTO.AuthDTO.LoginRequest;
import com.onestep.business_management.DTO.AuthDTO.UserBuyerInfoRequest;
import com.onestep.business_management.DTO.AuthDTO.LoginResponse;
import com.onestep.business_management.DTO.AuthDTO.UserBuyerInfoRespont;
import com.onestep.business_management.DTO.AuthDTO.UserchangesPassRequest;
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
import java.util.UUID;

@Controller
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/login")
    public ResponseEntity<?> login_form() {
        return ResponseEntity.ok().body(Map.of("response", "ok"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = authenticationService.login(loginRequest);
        ApiResponse<LoginResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(), // Status code 200
                "Login successfully",
                response,
                LocalDateTime.now() // Current date
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/google-sign-in")
    public ResponseEntity<?> loginWithGoogle(@RequestBody GoogleSignInRequest loginRequest) {
        LoginResponse response = authenticationService.google_sign_in(loginRequest);
        ApiResponse<LoginResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(), // Status code 200
                "Login successfully",
                response,
                LocalDateTime.now() // Current date
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/create_account")
    public ResponseEntity<?> create_account(@RequestBody User user) {
        authenticationService.create_account(user);
        return ResponseEntity.ok().body(Map.of("user", user));
    }

    @PostMapping("/upload/image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(imageService.uploadImage(file));
    }

    @GetMapping("/user-info/{userId}")
    public ResponseEntity<?> getUserInfo(@PathVariable UUID userId) {
        UserBuyerInfoRespont userInfo = authenticationService.getUserInfo(userId);

        ApiResponse<UserBuyerInfoRespont> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(), // Mã trạng thái 200
                "Lấy thông tin người dùng thành công",
                userInfo,
                LocalDateTime.now() // Ngày hiện tại
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/update-user/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable UUID userId,
            @RequestBody UserBuyerInfoRequest userBuyerInfoRequest) {

        // Lấy thông tin người dùng hiện tại từ cơ sở dữ liệu
        User currentUser = authenticationService.getUserById(userId);
        boolean isAnyFieldUpdated = false;

        // Kiểm tra sự thay đổi từng trường
        if (userBuyerInfoRequest.getFullName() != null
                && !userBuyerInfoRequest.getFullName().equals(currentUser.getFullName())) {
            isAnyFieldUpdated = true;
        }

        if (userBuyerInfoRequest.getEmail() != null
                && !userBuyerInfoRequest.getEmail().equals(currentUser.getEmail())) {
            isAnyFieldUpdated = true;
        }

        if (userBuyerInfoRequest.getPhoneNumber() != null
                && !userBuyerInfoRequest.getPhoneNumber().equals(currentUser.getPhoneNumber())) {
            isAnyFieldUpdated = true;
        }

        // Nếu không có trường nào thay đổi, trả về thông báo không thay đổi
        if (!isAnyFieldUpdated) {
            ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Không có gì thay đổi",
                    null, LocalDateTime.now());
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

        // Nếu có ít nhất một trường thay đổi, gọi phương thức update trong service
        UserBuyerInfoRespont updatedUserInfo = authenticationService.updateUserInfo(userId, userBuyerInfoRequest);

        ApiResponse<UserBuyerInfoRespont> apiResponse = new ApiResponse<>(HttpStatus.OK.value(),
                "Cập nhật thông tin người dùng thành công", updatedUserInfo, LocalDateTime.now());

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/change-password/{userId}")
    public ResponseEntity<?> changePassword(@PathVariable UUID userId,
            @RequestBody UserchangesPassRequest passwordRequest) {

        UserBuyerInfoRespont updatedUserInfo = authenticationService.changePassword(userId, passwordRequest);
        ApiResponse<UserBuyerInfoRespont> apiResponse = new ApiResponse<>(HttpStatus.OK.value(),
                "Đổi mật khẩu thành công", updatedUserInfo, LocalDateTime.now());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
