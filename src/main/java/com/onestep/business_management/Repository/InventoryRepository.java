package com.onestep.business_management.Repository;

import com.onestep.business_management.Entity.Inventory;
import com.onestep.business_management.Entity.Product;
import com.onestep.business_management.Entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    Optional<Inventory> findByBarcode(String barcode);

    @Query("SELECT i FROM Inventory i WHERE i.store.storeId LIKE %:storeId% ")
    List<Inventory> findInventoryByStore(@Param("storeId") UUID storeId);

}
