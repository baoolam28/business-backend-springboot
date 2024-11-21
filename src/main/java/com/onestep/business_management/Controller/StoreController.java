package com.onestep.business_management.Controller;

import com.onestep.business_management.DTO.API.ApiResponse;
import com.onestep.business_management.DTO.StoreDTO.StoreRequest;
import com.onestep.business_management.DTO.StoreDTO.StoreResponse;
import com.onestep.business_management.Service.StoreService.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;
@RestController
@RequestMapping("/api/buyer/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @PostMapping
public ResponseEntity<?> createOrUpdateStore(@RequestBody StoreRequest storeRequest) {
    System.out.println("store request: " + storeRequest);
    StoreResponse storeResponse = storeService.saveStore(storeRequest);
    ApiResponse<StoreResponse> apiResponse = new ApiResponse<>(
            HttpStatus.OK.value(),  // Mã trạng thái 200
            "Store created or updated by ",
            storeResponse,
            LocalDateTime.now()  // Ngày giờ hiện tại
    );
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
}


    @GetMapping("/{id}")
    public ResponseEntity<StoreResponse> getStoreById(@PathVariable String id) {
        UUID storeId = UUID.fromString(id);
        StoreResponse storeResponse = storeService.getStoreById(storeId);
        if (storeResponse != null) {
            return new ResponseEntity<>(storeResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // @GetMapping
    // public ResponseEntity<List<StoreResponse>> getAllStores() {
    //     List<StoreResponse> stores = storeService.getAllStores();
    //     return new ResponseEntity<>(stores, HttpStatus.OK);
    // }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStoreById(@PathVariable Integer id) {
        // do something 
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
