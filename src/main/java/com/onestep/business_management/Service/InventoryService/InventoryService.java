package com.onestep.business_management.Service.InventoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onestep.business_management.DTO.InventoryDTO.InventoryRequest;
import com.onestep.business_management.DTO.InventoryDTO.InventoryResponse;
import com.onestep.business_management.Entity.Inventory;
import com.onestep.business_management.Repository.InventoryRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    // Create or Update Inventory
    public InventoryResponse saveInventory(InventoryRequest inventoryRequest) {
        Inventory inventory = (Inventory) inventoryRepository.findByBarcode(inventoryRequest.getBarcode())
                .orElse(new Inventory());

        inventory.setBarcode(inventoryRequest.getBarcode());

        // Adjust the quantity in stock
        inventory.setQuantityInStock(inventory.getQuantityInStock() + inventoryRequest.getQuantityInStock());

        inventory.setLastUpdated(new Date());
        Inventory savedInventory = inventoryRepository.save(inventory);
        return InventoryMapper.INSTANCE.toResponse(savedInventory);
    }

    // Get Inventory by ID
    public InventoryResponse getInventoryById(Integer id) {
        Optional<Inventory> inventory = inventoryRepository.findById(id);
        return inventory.map(InventoryMapper.INSTANCE::toResponse).orElse(null);
    }

    // Get All Inventories
    public List<InventoryResponse> getAllInventories() {
        List<Inventory> inventories = inventoryRepository.findAll();
        return inventories.stream()
                .map(InventoryMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    // Delete Inventory by ID
    public void deleteInventoryById(Integer id) {
        inventoryRepository.deleteById(id);
    }

    public List<InventoryResponse> getInventoryByStore(UUID storeId){
        List<Inventory> inventories = inventoryRepository.findInventoryByStore(storeId);
        return inventories.stream()
                .map(InventoryMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }
}
