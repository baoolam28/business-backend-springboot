package com.onestep.business_management.Repository;

import com.onestep.business_management.Entity.Permission;
import com.onestep.business_management.Entity.ProductDetail;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {
    Optional<ProductDetail> findById(Integer productDetailId);
}
