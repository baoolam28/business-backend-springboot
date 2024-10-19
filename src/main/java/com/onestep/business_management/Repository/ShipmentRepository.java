package com.onestep.business_management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

import com.onestep.business_management.Entity.Shipment;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {
    // @Query("SELECT s FROM Shipment s JOIN s.orderOnline o WHERE o.user.userId = :userId")
    // List<Shipment> findByUserId(UUID userId);
}
