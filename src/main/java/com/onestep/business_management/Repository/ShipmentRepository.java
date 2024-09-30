package com.onestep.business_management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onestep.business_management.Entity.Shipment;

public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {
    
}
