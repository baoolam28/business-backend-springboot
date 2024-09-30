package com.onestep.business_management.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onestep.business_management.Entity.OrderOnline;

public interface OrderOnlineRepository extends JpaRepository<OrderOnline, UUID> {
    
}
