// package com.onestep.business_management.Controller;

// import com.onestep.business_management.DTO.API.ApiResponse;
// import com.onestep.business_management.DTO.PaymentDTO.VNPayRequest;
// import com.onestep.business_management.Service.AutoPaymentService.AutoPaymentService;
// import com.onestep.business_management.Service.AutoPaymentService.WebHookService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.time.LocalDateTime;
// import java.util.Map;
// import java.util.UUID;

// @RestController
// @RequestMapping("/api/seller/auto-payment")
// public class AutoPaymentController {

//     @Autowired
//     private AutoPaymentService autoPaymentService;

//     @Autowired
//     private WebHookService webHookService;

//     @PostMapping("/updatePayment")
//     public ResponseEntity<ApiResponse<Boolean>> updatePayment(@RequestBody Map<String, UUID> body) {
//         try {
//             UUID orderId = body.get("orderId");
//             boolean response = autoPaymentService.autoUpdatePayment(orderId);


//             if(response){
//                 webHookService.sendWebhook(orderId);
//                 ApiResponse<Boolean> apiResponse = new ApiResponse<>(
//                         HttpStatus.OK.value(),
//                         "Update payment successfully [Paid]",
//                         response,
//                         LocalDateTime.now()
//                 );
//                 return ResponseEntity.ok(apiResponse);
//             }

//             ApiResponse<Boolean> apiResponse = new ApiResponse<>(
//                     HttpStatus.OK.value(),
//                     "Update payment Failed [Pending]",
//                     response,
//                     LocalDateTime.now()
//             );
//             return ResponseEntity.ok(apiResponse);


//         } catch (Exception e) {
//             ApiResponse<Boolean> errorResponse = new ApiResponse<>(
//                     HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                     "Error updating payment: " + e.getMessage(),
//                     false,
//                     LocalDateTime.now()
//             );

//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
//         }
//     }

//     @PostMapping("/UI")
//     public String updateUI(@RequestBody Map<String, UUID> body) {
//         UUID orderId = body.get("orderId");
//         webHookService.sendWebhook(orderId);
//         return "update UI";
//     }
// }
