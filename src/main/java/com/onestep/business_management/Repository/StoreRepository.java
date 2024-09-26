package com.onestep.business_management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onestep.business_management.Entity.Store;
import com.onestep.business_management.Entity.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {
    List<Store> findByStoreManager(User storeManager);
}