// package com.onestep.business_management.Controller.BuyerController;

// import java.time.LocalDateTime;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestHeader;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.onestep.business_management.DTO.API.ApiResponse;
// import com.onestep.business_management.DTO.AuthDTO.BuyerRegistrationRequest;
// import com.onestep.business_management.DTO.AuthDTO.BuyerRegistrationResponse;
// import com.onestep.business_management.DTO.ForgotPasswordDTO.ResetPasswordWithEmailRequest;
// import com.onestep.business_management.DTO.ForgotPasswordDTO.ResetPasswordWithPhoneNumberRequest;
// import com.onestep.business_management.DTO.ForgotPasswordDTO.ResetPasswordResponse;
// import com.onestep.business_management.Service.AuthService.AuthenticationService;

// @RestController
// @RequestMapping("/api/buyer")
// public class BuyerResetPasswordController {
//     @Autowired
//     private AuthenticationService authenticationService;

//     @PutMapping("/send-mail")
//     public ResponseEntity<?> sendEmail (@RequestBody ResetPasswordWithEmailRequest request) {
//           try {
//                ResetPasswordResponse response = authenticationService.sendEmail(request.getEmail());
//                ApiResponse<ResetPasswordResponse> apiResponse = new ApiResponse<>(
//                     HttpStatus.OK.value(),
//                     "forgot password retrieved successfully",
//                     response,
//                     LocalDateTime.now()
//                );
//             return new ResponseEntity<>(apiResponse, HttpStatus.OK);
//           } catch (Exception e) {
//                System.out.println("Error retrieving products: " + e.getMessage());
//                ApiResponse errorResponse = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
//                return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//           }
//     }

//     @PutMapping("/reset-password-email")
//      public ResponseEntity<?> resetPasswordWithEmail(@RequestBody ResetPasswordWithEmailRequest request) {
//           try {
//                ResetPasswordResponse response = authenticationService.resetPasswordWithEmail(request);
//                ApiResponse<ResetPasswordResponse> apiResponse = new ApiResponse<>(
//                     HttpStatus.OK.value(),
//                     "Password reset successfully via email",
//                     response,
//                     LocalDateTime.now()
//                );
//                return new ResponseEntity<>(apiResponse, HttpStatus.OK);
//           } catch (Exception e) {
//                System.out.println("Error retrieving products: " + e.getMessage());
//                ApiResponse errorResponse = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
//                return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//           }
//      }

//      @PutMapping("/reset-password-phone")
//      public ResponseEntity<?> resetPasswordWithPhone(@RequestBody ResetPasswordWithPhoneNumberRequest request) {
//           try {
//                ResetPasswordResponse response = authenticationService.resetPasswordWithPhoneNumber(request);
//                ApiResponse<ResetPasswordResponse> apiResponse = new ApiResponse<>(
//                     HttpStatus.OK.value(),
//                     "Password reset successfully via phone",
//                     response,
//                     LocalDateTime.now()
//                );
//                return new ResponseEntity<>(apiResponse, HttpStatus.OK);
//           } catch (Exception e) {
//                ApiResponse errorResponse = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
//                return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//           }
//      }
// }
