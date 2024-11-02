package com.onestep.business_management.Controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onestep.business_management.DTO.API.ApiResponse;
import com.onestep.business_management.DTO.OtpDTO.OtpRequest;
import com.onestep.business_management.DTO.OtpDTO.OtpResponse;
import com.onestep.business_management.DTO.OtpDTO.VerifyOtpRequest;
import com.onestep.business_management.Service.OTPService.OtpService;

@RestController
@RequestMapping("/api/buyer")
public class OTPController {
    @Autowired
    private OtpService otpService;

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOTP(@RequestBody OtpRequest request){
        try {
            OtpResponse response = otpService.sendOtp(request.getPhoneNumber());
            ApiResponse<OtpResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Products retrieved successfully",
                response,
                LocalDateTime.now()
            );
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error retrieving products: " + e.getMessage());
            ApiResponse errorResponse = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOTP (@RequestBody VerifyOtpRequest request){
        boolean isVerified = otpService.verifyOtp(request.getPhoneNumber(), request.getOtpCode());
        if(isVerified){
           // Phản hồi trong trường hợp OTP hợp lệ
            ApiResponse<String> successResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "OTP xác minh thành công",
                "OTP hợp lệ",  // Thêm thông tin thành công nếu cần
                LocalDateTime.now()
            );
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        else{
           // Phản hồi trong trường hợp OTP không hợp lệ hoặc hết hạn
            ApiResponse<String> errorResponse = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "OTP không hợp lệ hoặc đã hết hạn",
                "Vui lòng yêu cầu mã OTP mới và thử lại.",  // Thông tin chi tiết về lỗi
                LocalDateTime.now()
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
    
}
