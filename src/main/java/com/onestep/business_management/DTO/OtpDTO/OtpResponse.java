package com.onestep.business_management.DTO.OtpDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Builder
public class OtpResponse {
    private String status;
    private String message;
    private String otpCode; // Thêm trường OTP
    private String phoneNumber; // Thêm trường số điện thoại
    private String expiryTime;
    private String errorCode;

//    // Constructor cho trường hợp thành công
//    public OtpResponse(String status, String message, String otpCode, String phoneNumber, String expiryTime) {
//     this.status = status;
//     this.message = message;
//     this.otpCode = otpCode;
//     this.phoneNumber = phoneNumber;
//     this.expiryTime = expiryTime;
// }

//     // Constructor cho trường hợp lỗi với mã lỗi
//     public OtpResponse(String status, String message, String errorCode) {
//         this.status = status;
//         this.message = message;
//         this.errorCode = errorCode;
//     }

//    // Getters và Setters
//    public String getStatus() { return status; }
//    public void setStatus(String status) { this.status = status; }

//    public String getMessage() { return message; }
//    public void setMessage(String message) { this.message = message; }

//    public String getOtpCode() { return otpCode; }
//    public void setOtpCode(String otpCode) { this.otpCode = otpCode; }

//    public String getPhoneNumber() { return phoneNumber; }
//    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

//    public String getExpiryTime() { return expiryTime; }
//    public void setExpiryTime(String expiryTime) { this.expiryTime = expiryTime; }

//    public String getErrorCode() { return errorCode; }
//    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }
}
