package com.onestep.business_management.Repository;

import com.onestep.business_management.Entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID> {
    @Query("SELECT d FROM Document d WHERE d.store.storeId = :storeId")
    List<Document> findAllByStore(UUID storeId);
}
