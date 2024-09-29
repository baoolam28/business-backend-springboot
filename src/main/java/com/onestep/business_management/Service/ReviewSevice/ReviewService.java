package com.onestep.business_management.Service.ReviewSevice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onestep.business_management.Entity.Review;
import com.onestep.business_management.Repository.ReviewRepository;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;


    
    public boolean deleteReview(Integer reviewId){
        Review existingReview = reviewRepository.findById(reviewId).orElse(null);
        if(existingReview != null){
            reviewRepository.delete(existingReview);
            return ReviewMapper.INSTANCE.toResponse(existingReview);
        }
        return null;
    }
}
