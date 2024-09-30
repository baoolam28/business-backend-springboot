package com.onestep.business_management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onestep.business_management.Entity.ShippingAddress;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Integer>{
    
}
