package com.onestep.business_management.Controller;

import com.onestep.business_management.DTO.InventoryDTO.InventoryRequest;
import com.onestep.business_management.DTO.InventoryDTO.InventoryResponse;
import com.onestep.business_management.Service.InventoryService.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // Get All Inventories
    @GetMapping
    public ResponseEntity<List<InventoryResponse>> getAllInventories() {
        try {
            List<InventoryResponse> response = inventoryService.getAllInventories();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get Inventory by ID
    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponse> getInventoryById(@PathVariable Integer id) {
        try {
            InventoryResponse response = inventoryService.getInventoryById(id);
            if (response != null) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create or Update Inventory
    @PostMapping
    public ResponseEntity<InventoryResponse> createOrUpdateInventory(@RequestBody InventoryRequest inventoryRequest) {
        try {
            InventoryResponse response = inventoryService.saveInventory(inventoryRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update Inventory by ID
    @PutMapping("/{id}")
    public ResponseEntity<InventoryResponse> updateInventory(@PathVariable Integer id,
            @RequestBody InventoryRequest inventoryRequest) {
        try {
            InventoryResponse response = inventoryService.saveInventory(inventoryRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete Inventory by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Integer id) {
        try {
            inventoryService.deleteInventoryById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
