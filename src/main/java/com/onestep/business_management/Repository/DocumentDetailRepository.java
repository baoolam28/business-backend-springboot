package com.onestep.business_management.Repository;

import com.onestep.business_management.Entity.DocumentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentDetailRepository extends JpaRepository<DocumentDetail, UUID> {
}
