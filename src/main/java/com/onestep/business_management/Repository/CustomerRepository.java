package com.onestep.business_management.Repository;

import com.onestep.business_management.Entity.Customer;
import com.onestep.business_management.Entity.OrderOffline;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
import java.util.UUID;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findCustomerByPhone (String phoneNumber);
    
    Optional<Customer> findByCustomerId(Integer customerId);

    @Query(value = "SELECT * FROM customers o WHERE o.store_id = :storeId", nativeQuery = true)
    List<Customer> findBystore(@Param("storeId") UUID storeId);
}
