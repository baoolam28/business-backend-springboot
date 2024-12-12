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
import com.onestep.business_management.Entity.Image;
import com.onestep.business_management.Entity.Product;
import com.onestep.business_management.Entity.ProductDetail;
import com.onestep.business_management.Entity.Review;
import com.onestep.business_management.Entity.User;
import com.onestep.business_management.Exeption.ResourceNotFoundException;
import com.onestep.business_management.Repository.ProductDetailRepository;
import com.onestep.business_management.Repository.ProductRepository;
import com.onestep.business_management.Repository.ReviewRepository;
import com.onestep.business_management.Utils.MapperService;
import com.onestep.business_management.Utils.StringToMapConverter;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MapperService mapperService;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private ProductRepository productRepository;



    public ProductReviewResponse getAllReviewByProductId(Integer productId){
        Product product = productRepository.findById(productId).orElseThrow(
            () -> new ResourceNotFoundException("not found product by productId " + productId)
        );
        ProductReviewResponse response = new ProductReviewResponse();
        List<Integer> productDetailIds = product.getProductDetails()
            .stream()
            .map(ProductDetail::getProductDetailId)
            .collect(Collectors.toList());
        
        if(productDetailIds == null){
            return new ProductReviewResponse(0, new ArrayList<>());
        }

        List<Review> reviews = reviewRepository.findAllReviewsByProductDetailIds(productDetailIds).orElse(null);
        if(reviews != null){
            List<ReviewResponse> reviewResponseList = reviews.stream()
            .map(review -> {
                ReviewResponse reviewResponse = ReviewMapper.INSTANCE.toResponse(review);
                reviewResponse.setImages(review.getImageUrls());
                return reviewResponse;
            })
            .collect(Collectors.toList());
        
            response.setTotalReview(reviewResponseList.size());
            response.setReview(reviewResponseList);
        }
        return response;
    }

    public ReviewResponse getReviewByProductDetailId(Integer productDetailId, UUID userId){
        Review review = reviewRepository.findReviewByProductDetailId(productDetailId, userId).orElseThrow(
            () -> new ResourceNotFoundException("Review not found for productDetailId: " + productDetailId + " and userId: " + userId)
        );
        ReviewResponse response = ReviewMapper.INSTANCE.toResponse(review);
        if(review.getImageUrls() != null){
            response.setImages(review.getImageUrls());
        }
        return response;
    }
    
    public ReviewResponse createNewReview(ReviewRequest reviewRequest){
        try {
            ReviewResponse response = new ReviewResponse();

        ProductDetail productDetail = productDetailRepository.findById(reviewRequest.getProductDetailId()).orElseThrow(
            () -> new ResourceNotFoundException("ProductDetail not found")
        );
        Product product = productDetail.getProduct();
        User user = mapperService.findUserById(reviewRequest.getUserId());
        Review newReview = ReviewMapper.INSTANCE.toEntity(reviewRequest);
        if(reviewRequest.getImages() != null){
            List<Image> imagesUploaded = mapperService.uploadImages(reviewRequest.getImages());
            if(imagesUploaded.size() > 0 && imagesUploaded != null){
                List<String> imagesRes = new ArrayList<>();
                for(Image image : imagesUploaded){
                    imagesRes.add(image.getFileName());
                    response.setImages(imagesRes);
                    newReview.setImageUrls(imagesRes);
                }
            }
        }

        newReview.setProductDetail(productDetail);
        newReview.setUser(user);
        newReview.setIsReviewed(true);
        newReview.setReviewDate(new Date());
        Review saveReview = reviewRepository.save(newReview);
        response.setReviewId(saveReview.getReviewId());
        response.setUsername(user.getUsername());
        response.setReviewDate(saveReview.getReviewDate());
        response.setRating(saveReview.getRating());
        response.setProductName(product.getProductName());
        response.setProductDetailId(reviewRequest.getProductDetailId());
        response.setIsReviewed(saveReview.getIsReviewed());
        Image avatar = user.getImage();
        if(avatar != null) {
            response.setImageUser(avatar.getFileName());
        }
        List<Image> productImages = product.getImages();
        
        if(productImages.size() > 0 && productImages != null){
            Image productImage = productImages.get(0);
            response.setProductImage(productImage.getFileName());
        }
        response.setComment(saveReview.getComment());
        response.setAttributes(StringToMapConverter.convertStringToMap(productDetail.getAttributes()));
        return response;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
        
    }

    public boolean checkIfReviewed(Integer productDetailId, UUID userId){
        if (productDetailId == null || userId == null) {
            throw new IllegalArgumentException("productDetailId and userId must not be null");
        }
        return reviewRepository.findReviewByProductDetailId(productDetailId, userId).isPresent();
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
