package com.onestep.business_management.Service.StoreService;

import com.onestep.business_management.DTO.StoreDTO.StoreIsActiveRequest;
import com.onestep.business_management.DTO.StoreDTO.StoreRequest;
import com.onestep.business_management.DTO.StoreDTO.StoreResponse;
import com.onestep.business_management.Entity.Role;
import com.onestep.business_management.Entity.Store;
import com.onestep.business_management.Entity.User;
import com.onestep.business_management.Exeption.ResourceNotFoundException;
import com.onestep.business_management.Repository.StoreRepository;
import com.onestep.business_management.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private UserRepository userRepository;

    // Create or Update Store
    public StoreResponse saveStore(StoreRequest storeRequest) {
        UUID managerId = storeRequest.getStoreManagerId();
        User storeManager = userRepository.findById(managerId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + managerId + " not found."));

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
    public StoreResponse getStoreById(UUID storeId) {
        Optional<Store> store = storeRepository.findById(storeId);
        return store.map(StoreMapper.INSTANCE::toResponse).orElse(null);
    }

    // Get All Stores
    public List<StoreResponse> getAllStores() {
        List<Store> stores = storeRepository.findAll();
        return stores.stream()
                .map(StoreMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    public StoreResponse getStoreByUser(UUID userId){
        Store result =  new Store();
        User exitsUser = userRepository.findById(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + userId + " not found.")
        );

        Set<Role> roleTaken = exitsUser.getRoles();
        Role curentRole = new Role();

        if(roleTaken.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role with id " + userId + " not found.");
        }

        curentRole = roleTaken.iterator().next();

        if (curentRole.getRoleName().equals("ROLE_SELLER")){
            List<Store> stores = storeRepository.findByStoreManager(exitsUser);
            if(stores.isEmpty()){
                throw new ResourceNotFoundException("Store not found!");
            }
            result = stores.get(0);
        }

        if(curentRole.getRoleName().equals("ROLE_STAFF")){
            UUID storeId = exitsUser.getStore().getStoreId();

            result = storeRepository.findById(storeId).orElseThrow(
                    () -> new ResourceNotFoundException("Store not found!")
            );
        }


        return StoreMapper.INSTANCE.toResponse(result);
    }

    // Delete Store by ID
    public void deleteStoreById(Integer id) {
        // do something
    }

    // Update Store Status (isActive)
    public StoreResponse updateStoreStatus(StoreIsActiveRequest storeIsActiveRequest) {
        UUID storeId = storeIsActiveRequest.getStoreId();
        boolean isActive = storeIsActiveRequest.isActive();

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("Store with id " + storeId + " not found."));

        // Cập nhật trạng thái isActive
        store.setActive(isActive);
        store.setUpdatedAt(LocalDateTime.now());

        Store updatedStore = storeRepository.save(store);
        return StoreMapper.INSTANCE.toResponse(updatedStore);
    }

}
