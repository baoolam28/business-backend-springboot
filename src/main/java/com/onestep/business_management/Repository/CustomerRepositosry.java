package com.onestep.business_management.Repository;

import com.onestep.business_management.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface CustomerRepositosry extends JpaRepository<Customer, Integer> {

    Optional<Customer> findCustomerByPhone(String phoneNumber);

    Optional<Customer> findByCustomerId(Integer customerId);

}
