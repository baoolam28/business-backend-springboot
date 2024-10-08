package com.onestep.business_management.Service.StoreService;

import com.onestep.business_management.DTO.StoreRequest;
import com.onestep.business_management.DTO.StoreResponse;
import com.onestep.business_management.Entity.Store;
import com.onestep.business_management.Entity.User;
import com.onestep.business_management.Repository.StoreRepository;
import com.onestep.business_management.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private UserRepository userRepository;

    // Create or Update Store
    public StoreResponse saveStore(StoreRequest storeRequest) {
        Integer managerId = storeRequest.getStoreManagerId();
        User storeManager = userRepository.findById(managerId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + managerId + " not found.")
        );

        List<Store> checkStoreCreated = storeRepository.findByStoreManager(storeManager);

        if (!checkStoreCreated.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already has a store.");
        }

        Store store = new Store();
        store.setStoreName(storeRequest.getStoreName());
        store.setStoreLocation(storeRequest.getStoreLocation());
        store.setStoreManager(storeManager);

        if (store.getCreatedAt() == null) {
            store.setCreatedAt(LocalDateTime.now());
        }
        store.setUpdatedAt(LocalDateTime.now());

        Store savedStore = storeRepository.save(store);
        return StoreMapper.INSTANCE.toResponse(savedStore);
    }

    // Get Store by ID
    public StoreResponse getStoreById(Integer id) {
        Optional<Store> store = storeRepository.findById(id);
        return store.map(StoreMapper.INSTANCE::toResponse).orElse(null);
    }

    // Get All Stores
    public List<StoreResponse> getAllStores() {
        List<Store> stores = storeRepository.findAll();
        return stores.stream()
                .map(StoreMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    // Delete Store by ID
    public void deleteStoreById(Integer id) {
        // do something 
    }
}
