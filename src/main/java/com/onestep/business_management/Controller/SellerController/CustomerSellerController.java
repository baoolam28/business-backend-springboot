package com.onestep.business_management.Controller.SellerController;

import com.onestep.business_management.DTO.API.ApiResponse;
import com.onestep.business_management.DTO.CustomerDTO.CustomerRequest;
import com.onestep.business_management.DTO.CustomerDTO.CustomerResponse;
import com.onestep.business_management.DTO.ProductDTO.ProductResponse;
import com.onestep.business_management.Service.CustomerService.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/seller/customers")
public class CustomerSellerController {
    
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getCustomers() {
        try {
            List<CustomerResponse> response = customerService.getAllCustomers();
            ApiResponse<List<CustomerResponse>> phanHoiApi = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Đã lấy tất cả khách hàng thành công",
                    response,
                    LocalDateTime.now());

            // In status code và thông điệp
            System.out.println("Status Code: " + HttpStatus.OK.value() + " - " + phanHoiApi.getMessage());
            return new ResponseEntity<>(phanHoiApi, HttpStatus.OK);
        } catch (Exception e) {
            // In thông báo lỗi
            System.out.println("Lỗi khi lấy khách hàng: " + e.getMessage());

            ApiResponse<String> phanHoiLoi = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Đã xảy ra lỗi khi lấy khách hàng",
                    null,
                    LocalDateTime.now());

            // In status code và thông điệp lỗi
            System.out.println(
                    "Status Code: " + HttpStatus.INTERNAL_SERVER_ERROR.value() + " - " + phanHoiLoi.getMessage());
            return new ResponseEntity<>(phanHoiLoi, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody CustomerRequest customerRequest) {
        CustomerResponse response = customerService.createCustomer(customerRequest);
         ApiResponse<CustomerResponse> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),  // Status code 200
                    "Customer created successfully",
                    response,
                    LocalDateTime.now()  // Current date
            );
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable int id,
            @RequestBody CustomerRequest customerRequest) {
        try {
            CustomerResponse response = customerService.updateCustomer(id, customerRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int id) {
        try {
            customerService.deleteCustomer(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<ApiResponse<?>> getCustomersByStoreId(@PathVariable UUID storeId) {
        try {
            List<CustomerResponse> response = customerService.getCustomersByStoreId(storeId);
            ApiResponse<List<CustomerResponse>> phanHoiApi = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Đã lấy tất cả khách hàng theo storeId thành công",
                    response,
                    LocalDateTime.now());

            // In status code và thông điệp
            System.out.println("Status Code: " + HttpStatus.OK.value() + " - " + phanHoiApi.getMessage());
            return new ResponseEntity<>(phanHoiApi, HttpStatus.OK);
        } catch (Exception e) {
            // In thông báo lỗi
            System.out.println("Lỗi khi lấy khách hàng theo storeId: " + e.getMessage());

            ApiResponse<String> phanHoiLoi = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Đã xảy ra lỗi khi lấy khách hàng theo storeId",
                    null,
                    LocalDateTime.now());

            // In status code và thông điệp lỗi
            System.out.println(
                    "Status Code: " + HttpStatus.INTERNAL_SERVER_ERROR.value() + " - " + phanHoiLoi.getMessage());
            return new ResponseEntity<>(phanHoiLoi, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
