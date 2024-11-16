package com.onestep.business_management.DTO.OtpDTO;

public class VerifyOtpRequest {
    private String phoneNumber;
    private String otpCode;

    // Getter và Setter
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getOtpCode() { return otpCode; }
    public void setOtpCode(String otpCode) { this.otpCode = otpCode; }
}
