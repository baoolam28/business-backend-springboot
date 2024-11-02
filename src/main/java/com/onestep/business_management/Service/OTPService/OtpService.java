package com.onestep.business_management.Service.OTPService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.onestep.business_management.DTO.OtpDTO.OtpResponse;
import com.onestep.business_management.Repository.OTPRepository;
import java.util.Random;

@Service
public class OtpService {

    private Map<String, String> otpStorage = new HashMap<>();  // Tạm lưu OTP theo phoneNumber
    private Map<String, LocalDateTime> otpExpiry = new HashMap<>();

    @Autowired
    private OTPRepository otpRepository;

    public OtpResponse sendOtp(String phoneNumber) {
        String otpCode = generateRandomOtp(6); // Tạo mã OTP 6 chữ số

        otpStorage.put(phoneNumber, otpCode);

        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5);
        otpExpiry.put(phoneNumber, expiryTime);
        String expiryTimeString = expiryTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        boolean isSent = otpRepository.callOtp(phoneNumber, otpCode);
        
        // Trả về OtpResponse với thông tin chi tiết
        if (isSent) {
            return OtpResponse.builder()
                    .status("success")
                    .message("OTP đã được gửi thành công!")
                    .otpCode(otpCode)
                    .phoneNumber(phoneNumber)
                    .expiryTime(expiryTimeString)
                    .build();
        } else {
            return OtpResponse.builder()
                    .status("error")
                    .message("Gửi OTP thất bại!")
                    .phoneNumber(phoneNumber)
                    .errorCode("ERR001")
                    .build();
        }
    }

    private String generateRandomOtp(int length) {
        Random random = new Random();
        StringBuilder otp = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            otp.append(random.nextInt(10)); // Thêm số ngẫu nhiên từ 0 đến 9
        }

        return otp.toString();
    }

    public boolean verifyOtp(String phoneNumber, String otpCode){
        if (!otpStorage.containsKey(phoneNumber)) {
            System.out.println("No OTP found for this phone number: " + phoneNumber);
            return false;
        }
        if (!otpStorage.get(phoneNumber).equals(otpCode)) {
            System.out.println("Entered OTP does not match for phone number: " + phoneNumber);
            return false;
        }

        // Kiểm tra thời gian hết hạn
        LocalDateTime expiryTime = otpExpiry.get(phoneNumber);
        System.out.println(expiryTime);
        if (expiryTime.isBefore(LocalDateTime.now())) {
            otpStorage.remove(phoneNumber); 
            otpExpiry.remove(phoneNumber); 
            return false;
        }

        otpStorage.remove(phoneNumber);
        otpExpiry.remove(phoneNumber);
        return true;
    }


}
