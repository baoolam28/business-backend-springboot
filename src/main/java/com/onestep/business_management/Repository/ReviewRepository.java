package com.onestep.business_management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onestep.business_management.Entity.Review;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
   List<Review> findByProductProductId(Integer productId);

   @Query("SELECT r FROM Review r WHERE r.productDetail.productDetailId = :productDetailId AND r.user.userId = :userId")
   Optional<Review> findReviewByProductDetailId(@Param("productDetailId") Integer productDetailId, @Param("userId") UUID userId);

   @Query("SELECT r FROM Review r WHERE r.product.productId = :productId AND r.rating = :rating")
   List<Review> findReviewByRating(@Param("productId") Integer productId, @Param("rating") Integer rating);
}
