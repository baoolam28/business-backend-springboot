package com.onestep.business_management.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onestep.business_management.Entity.ShippingAddress;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, UUID> {
    @Query("SELECT sa FROM ShippingAddress sa WHERE sa.userId.userId = :userId")
    List<ShippingAddress> findByUserId(@Param("userId") UUID userId);
}
