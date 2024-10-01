package com.onestep.business_management.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onestep.business_management.Entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>{
    List<Review> findByProductId(Integer productId);
}
