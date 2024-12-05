package com.onestep.business_management.Service.ReviewSevice;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.onestep.business_management.DTO.ReviewDTO.ProductReviewRequest;
import com.onestep.business_management.DTO.ReviewDTO.ProductReviewResponse;
import com.onestep.business_management.DTO.ReviewDTO.ReviewRequest;
import com.onestep.business_management.DTO.ReviewDTO.ReviewResponse;
import com.onestep.business_management.Entity.Product;
import com.onestep.business_management.Entity.ProductDetail;
import com.onestep.business_management.Entity.Review;
import com.onestep.business_management.Entity.User;
import com.onestep.business_management.Exeption.ResourceNotFoundException;
import com.onestep.business_management.Repository.ProductDetailRepository;
import com.onestep.business_management.Repository.ReviewRepository;
import com.onestep.business_management.Utils.MapperService;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MapperService mapperService;

    @Autowired
    private ProductDetailRepository productDetailRepository;



    public ProductReviewResponse getAllReviewByProductId(Integer productId){
        List<Review> reviews = reviewRepository.findByProductProductId(productId);

        ProductReviewResponse response = new ProductReviewResponse();
       response.setTotalReview(reviews.size());
       List<ReviewResponse> reviewResponse = new ArrayList<>();
       for(Review review : reviews){
        reviewResponse.add(ReviewMapper.INSTANCE.toResponse(review));
       }
       response.setReview(reviewResponse);
       
        return response;
    }

    // public ProductReviewResponse getAllReviewByRating(Integer productId, Integer rating){
    //     List<Review> reviews = reviewRepository.findReviewByRating(productId, rating);
    //     if (reviews.isEmpty()) {
    //         return new ProductReviewResponse(0, List.of());
    //     }
    //    ProductReviewResponse response = new ProductReviewResponse();
    //    response.setTotalReview(reviews.size());
    //    List<ReviewResponse> reviewResponse = new ArrayList<>();
    //    for(Review review : reviews){
    //     reviewResponse.add(ReviewMapper.INSTANCE.toResponse(review));
    //    }
    //    response.setReview(reviewResponse);
       
    //     return response;
    // }

    public ReviewResponse getReviewByProductDetailId(Integer productDetailId, UUID userId){
        Review review = reviewRepository.findReviewByProductDetailId(productDetailId, userId).orElseThrow(
            () -> new ResourceNotFoundException("Review not found for productDetailId: " + productDetailId + " and userId: " + userId)
        );
        return ReviewMapper.INSTANCE.toResponse(review);
    }
    
    public ReviewResponse createNewReview(Integer productDetailId, ReviewRequest reviewRequest){

        ProductDetail productDetail = productDetailRepository.findById(productDetailId).orElseThrow(
            () -> new ResourceNotFoundException("ProductDetail not found")
        );
        Product product = productDetail.getProduct();
        User user = mapperService.findUserById(reviewRequest.getUserId());

        Review newReview = ReviewMapper.INSTANCE.toEntity(reviewRequest);
        newReview.setProduct(product);
        newReview.setProductDetail(productDetail);
        newReview.setUser(user);
        Review saveReview = reviewRepository.save(newReview);
        return ReviewMapper.INSTANCE.toResponse(saveReview);
    }

    private boolean canEditReview(Date reviewDate){
        LocalDateTime createdAt = reviewDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime now = LocalDateTime.now();
        Long hoursDifference = ChronoUnit.HOURS.between(createdAt, now);
        return hoursDifference <= 24;
    }

    public ReviewResponse updateReviewByReviewId(ReviewRequest reviewRequest, Integer reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
            () -> new ResourceNotFoundException("Review not found")
        );

        if(!canEditReview(review.getReviewDate())){
            throw new IllegalArgumentException("You can no longer edit this review as the time limit has passed.");
        }

        review.setComment(reviewRequest.getComment());
        review.setRating(reviewRequest.getRating());
        review.setImageUrls(reviewRequest.getImageUrls());
        review.setVideoUrl(reviewRequest.getVideoUrl());
        
        Review updatedReview = reviewRepository.save(review);

        return ReviewMapper.INSTANCE.toResponse(updatedReview);

    }

    public ReviewResponse deleteReviewByReviewId(Integer reviewId){
        Review review = reviewRepository.findById(reviewId).orElseThrow(
            () -> new ResourceNotFoundException("Review not found")
        );
        ReviewResponse response = ReviewMapper.INSTANCE.toResponse(review);
        reviewRepository.delete(review);
        return response;
    }
}
