package com.onestep.business_management.DTO.ForgotPasswordDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordWithEmailRequest {
    private String email;
    private String newPassword;

}
