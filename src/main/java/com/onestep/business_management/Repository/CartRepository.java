package com.onestep.business_management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onestep.business_management.Entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{
    
}
