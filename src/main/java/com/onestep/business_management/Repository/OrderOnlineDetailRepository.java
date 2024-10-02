package com.onestep.business_management.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onestep.business_management.Entity.OrderOnlineDetail;

@Repository
public interface OrderOnlineDetailRepository extends JpaRepository<OrderOnlineDetail, UUID>{
    
}
