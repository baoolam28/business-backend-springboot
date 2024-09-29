package com.onestep.business_management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onestep.business_management.Entity.CartItems;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Integer>{
    
}
