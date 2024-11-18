package com.onestep.business_management.Controller.SellerController;

import com.onestep.business_management.DTO.API.ApiResponse;
import com.onestep.business_management.DTO.DocumentDTO.DocumentRequest;
import com.onestep.business_management.DTO.DocumentDTO.DocumentResponse;
import com.onestep.business_management.DTO.OrderDTO.OrderRequest;
import com.onestep.business_management.DTO.OrderDTO.OrderResponse;
import com.onestep.business_management.Service.DocumentService.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/seller/documents")
public class DocumentSellerController {

    @Autowired
    private DocumentService documentService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getALlDocument(@PathVariable UUID id){
        try {
            List<DocumentResponse> response = documentService.getAllDocumentsByStore(id);
            ApiResponse<List<DocumentResponse>> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "lấy dữ liệu chứng từ thành công",
                    response,
                    LocalDateTime.now());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<String> apiResponse = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Đã xảy ra lỗi khi lấy dữ liệu chứng từ: " + e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> createDocument(@RequestBody DocumentRequest docRequest) {
        try {
            DocumentResponse response = documentService.createDocument(docRequest);
            ApiResponse<DocumentResponse> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Tạo chứng từ thành công",
                    response,
                    LocalDateTime.now());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<String> apiResponse = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Đã xảy ra lỗi khi tạo chứng từ: " + e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
