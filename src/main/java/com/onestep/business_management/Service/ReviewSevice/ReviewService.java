package com.onestep.business_management.Service.ReviewSevice;


import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onestep.business_management.DTO.ProductResponse;
import com.onestep.business_management.DTO.ReviewRequest;
import com.onestep.business_management.DTO.ReviewResponse;
import com.onestep.business_management.Entity.Review;
import com.onestep.business_management.Repository.ReviewRepository;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public List<ReviewResponse> getAllReviews(Integer productId){
       List<Review> reviews = reviewRepository.findByProductId(productId);
       return reviews.stream()
                .map(ReviewMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    public ReviewResponse getReviewById(Integer reviewId){
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if(review != null){
            return ReviewMapper.INSTANCE.toResponse(review);
        }
        return null;
    }

    public ReviewResponse createReview(ReviewRequest reviewRequest){
        Review newReview = ReviewMapper.INSTANCE.toEntity(reviewRequest);
        newReview = reviewRepository.save(newReview);
        return ReviewMapper.INSTANCE.toResponse(newReview);

    }

    public ReviewResponse updateReview(ReviewRequest reviewRequest){
        Review existingReview  = reviewRepository.findById(reviewRequest.getReviewId()).orElse(null);
        if(existingReview != null){
            Review updateReview = ReviewMapper.INSTANCE.toEntity(reviewRequest);
            Review saveReview = reviewRepository.save(updateReview);
            return ReviewMapper.INSTANCE.toResponse(saveReview);
        }
        return null;
    }
    
    public ReviewResponse deleteReview(Integer reviewId){
        Review existingReview = reviewRepository.findById(reviewId).orElse(null);
        if(existingReview != null){
            reviewRepository.delete(existingReview);
            return ReviewMapper.INSTANCE.toResponse(existingReview);
        }
        return null;
    }
}
