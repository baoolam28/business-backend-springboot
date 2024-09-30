package com.onestep.business_management.Controller;

import com.onestep.business_management.DTO.SupplierDTO.SupplierRequest;
import com.onestep.business_management.DTO.SupplierDTO.SupplierResponse;
import com.onestep.business_management.Service.SupplierService.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    // Endpoint để tạo một nhà cung cấp mới
    @PostMapping
    public ResponseEntity<SupplierResponse> createSupplier(@RequestBody SupplierRequest supplierRequest) {
        try {
            SupplierResponse response = supplierService.create_supplier(supplierRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint để lấy tất cả các nhà cung cấp
    @GetMapping
    public ResponseEntity<List<SupplierResponse>> getAllSuppliers() {
        try {
            List<SupplierResponse> response = supplierService.get_all();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    

    // Endpoint để tìm nhà cung cấp theo tên
    @GetMapping("/name")
    public ResponseEntity<SupplierResponse> getSupplierByName(@RequestParam("supplierName") String supplierName) {
        try {
            SupplierResponse response = supplierService.get_byName(supplierName);
            if (response != null) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Các endpoint khác
    @GetMapping("/search")
    public ResponseEntity<List<SupplierResponse>> searchByKeyword(@RequestParam String keyword) {
        try {
            List<SupplierResponse> response = supplierService.searchByKeyword(keyword);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/email")
    public ResponseEntity<List<SupplierResponse>> findByEmail(@RequestParam String email) {
        try {
            List<SupplierResponse> response = supplierService.findByEmail(email);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/phone")
    public ResponseEntity<List<SupplierResponse>> findByPhone(@RequestParam String phone) {
        try {
            List<SupplierResponse> response = supplierService.findByPhone(phone);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fax")
    public ResponseEntity<List<SupplierResponse>> findByFax(@RequestParam String fax) {
        try {
            List<SupplierResponse> response = supplierService.findByFax(fax);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/address")
    public ResponseEntity<List<SupplierResponse>> findByAddress(@RequestParam String address) {
        try {
            List<SupplierResponse> response = supplierService.findByAddress(address);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(("/{supplierId}"))
    public ResponseEntity<SupplierResponse> updateSupplier(@PathVariable Integer supplierId, @RequestBody SupplierRequest supplierRequest) {
        try {
            SupplierResponse response = supplierService.update_supplier(supplierRequest, supplierId);
            return response!= null? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Xử lý l��i nếu có
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    
}
