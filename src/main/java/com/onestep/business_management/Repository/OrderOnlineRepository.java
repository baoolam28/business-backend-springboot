package com.onestep.business_management.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.onestep.business_management.DTO.OrderDTO.OrderShippingRespons;
import com.onestep.business_management.Entity.OrderOnline;

@Repository
public interface OrderOnlineRepository extends JpaRepository<OrderOnline, UUID> {

    @Query("SELECT o FROM OrderOnline o WHERE o.user.userId = :userId")
    List<OrderOnline> findByUser(@Param("userId") UUID userId);

    @Query("SELECT o FROM OrderOnline o WHERE o.store.storeId = :storeId")
    List<OrderOnline> findByStoreId(@Param("storeId") UUID storeId);

    @Modifying
    @Transactional
    @Query("UPDATE OrderOnline o SET o.status = :newStatus WHERE o.status = :currentStatus AND o.store.storeId = :storeId")
    int updateOrderStatusByStoreID(@Param("currentStatus") OrderOnline.Status currentStatus,
            @Param("newStatus") OrderOnline.Status newStatus,
            @Param("storeId") UUID storeId);
}
