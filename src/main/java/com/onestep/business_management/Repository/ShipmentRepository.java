package com.onestep.business_management.Repository;

import com.onestep.business_management.Entity.OrderOnline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.onestep.business_management.Entity.Shipment;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {

    Optional<Shipment> findByOrderOnline(OrderOnline orderOnline);

    @Query("SELECT s FROM Shipment s WHERE s.orderOnline.orderOnlineId = :orderOnlineId")
    Optional<Shipment> findShipmentByOrderOnlineId(UUID orderOnlineId);

}
