package com.onestep.business_management.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onestep.business_management.Entity.Cart;
import com.onestep.business_management.Entity.User;

import java.util.List;


@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{
    Optional<Cart> findByUser(User user);
}
