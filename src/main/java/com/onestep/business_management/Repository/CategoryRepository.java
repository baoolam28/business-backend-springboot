package com.onestep.business_management.Repository;

import com.onestep.business_management.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @Query("SELECT p.category FROM Product p WHERE p.store.storeId = :storeId")
    Optional<List<Category>> findByStore(UUID storeId);
}
