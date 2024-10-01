package com.onestep.business_management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onestep.business_management.Entity.Store;
import com.onestep.business_management.Entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface StoreRepository extends JpaRepository<Store, UUID> {
    List<Store> findByStoreManager(User storeManager);
}