package com.onestep.business_management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onestep.business_management.Entity.OrderOnlineDetail;

public interface OrderOnlineDetailRepository extends JpaRepository<OrderOnlineDetail, Integer>{
    
}
