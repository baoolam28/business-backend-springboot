package com.onestep.business_management.Repository;

import com.onestep.business_management.Entity.OrderOfflineDetail;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderOfflineDetailRepository extends JpaRepository<OrderOfflineDetail,UUID> {
}
