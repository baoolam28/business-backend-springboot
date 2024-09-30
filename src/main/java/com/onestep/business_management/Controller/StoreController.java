package com.onestep.business_management.Controller;

import com.onestep.business_management.DTO.StoreDTO.StoreRequest;
import com.onestep.business_management.DTO.StoreDTO.StoreResponse;
import com.onestep.business_management.Service.StoreService.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @PostMapping
    public ResponseEntity<StoreResponse> createOrUpdateStore(@RequestBody StoreRequest storeRequest) {
        StoreResponse storeResponse = storeService.saveStore(storeRequest);
        return new ResponseEntity<>(storeResponse, HttpStatus.CREATED);
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

    @GetMapping
    public ResponseEntity<List<StoreResponse>> getAllStores() {
        List<StoreResponse> stores = storeService.getAllStores();
        return new ResponseEntity<>(stores, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStoreById(@PathVariable Integer id) {
        // do something 
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
