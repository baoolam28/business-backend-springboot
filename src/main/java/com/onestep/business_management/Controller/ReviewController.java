package com.onestep.business_management.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.onestep.business_management.DTO.API.ApiResponse;
import com.onestep.business_management.DTO.ProductDTO.ProductDetailResponse;
import com.onestep.business_management.DTO.ProductDTO.ProductOnlineResponse;
import com.onestep.business_management.DTO.ReviewDTO.ProductReviewRequest;
import com.onestep.business_management.DTO.ReviewDTO.ProductReviewResponse;
import com.onestep.business_management.DTO.ReviewDTO.ReviewRequest;
import com.onestep.business_management.DTO.ReviewDTO.ReviewResponse;
import com.onestep.business_management.Service.ProductService.ProductService;
import com.onestep.business_management.Service.ReviewSevice.ReviewService;

@RestController
@RequestMapping("/api/buyer/reviews")
public class ReviewController {
    
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductService productService;

    @GetMapping("/allReviews/{productId}")
    public ResponseEntity<?> getAllReviewProduct(@PathVariable Integer productId){
        try {
            ProductReviewResponse response = reviewService.getAllReviewByProductId(productId);
            ApiResponse<ProductReviewResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Get all review product successfully",
                response,
                LocalDateTime.now());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error retrieving review: " + e.getMessage());
            ApiResponse errorResponse = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{productDetailId}")
    public ResponseEntity<?> getProductDetail(@PathVariable Integer productDetailId){
        try {
            ProductDetailResponse response = productService.findProductDetailById(productDetailId);
            ApiResponse<ProductDetailResponse> apiResponse = new ApiResponse<>( 
                HttpStatus.OK.value(),
                "Retrieve product detail successfully",
                response,
                LocalDateTime.now());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error retrieving review: " + e.getMessage());
            ApiResponse errorResponse = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/review")
    public ResponseEntity<?> getReviewByProductDetailId(@RequestParam Integer productDetailId, @RequestParam UUID userId){
        try {
            ReviewResponse response = reviewService.getReviewByProductDetailId(productDetailId, userId);
            ApiResponse<ReviewResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Retrieve Review successfully",
                response,
                LocalDateTime.now());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error retrieving review: " + e.getMessage());
            ApiResponse errorResponse = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/newReview/{productDetailId}")
    public ResponseEntity<?> createNewReview(@PathVariable Integer productDetailId, @RequestBody ReviewRequest reviewRequest){
        try {
            ReviewResponse response = reviewService.createNewReview(productDetailId ,reviewRequest);
            ApiResponse<ReviewResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Create Review successfully",
                response,
                LocalDateTime.now());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error retrieving review: " + e.getMessage());
            ApiResponse errorResponse = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/is-reviewed")
    public ResponseEntity<?> isReviewed(@RequestParam(value = "productDetailId", required = false) Integer productDetailId, @RequestParam(value = "userId") UUID userId){
        try {
            boolean isReviewed = reviewService.checkIfReviewed(productDetailId, userId);
            return ResponseEntity.ok(isReviewed);
        } catch (Exception e) {
            System.out.println("Error retrieving review: " + e.getMessage());
            ApiResponse errorResponse = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update-review/{reviewId}")
    public ResponseEntity<?> updateReview(@RequestBody ReviewRequest reviewRequest, @PathVariable Integer reviewId){
        try {
            // Integer reviewId = reviewRequest.getReviewId();
            ReviewResponse response = reviewService.updateReviewByReviewId(reviewRequest, reviewId);
            ApiResponse<ReviewResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Update Review successfully",
                response,
                LocalDateTime.now());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error retrieving review: " + e.getMessage());
            ApiResponse errorResponse = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer reviewId){
        try {
            ReviewResponse response = reviewService.deleteReviewByReviewId(reviewId);
            ApiResponse<ReviewResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Dalete Review successfully",
                response,
                LocalDateTime.now());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error retrieving review: " + e.getMessage());
            ApiResponse errorResponse = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @PostMapping("/Save")
    // public ResponseEntity<?> upLoadFile(@RequestParam("file") MultipartFile file){
    //     try {
    //         String fileUrl = fileService.saveFile(file);
    //         ApiResponse<String> apiResponse = new ApiResponse<>(
    //             HttpStatus.OK.value(),
    //             "File uploaded successfully",
    //             fileUrl,
    //             LocalDateTime.now());
    //         return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    //     } catch (Exception e) {
    //         System.out.println("Error uploading file: " + e.getMessage());
    //         ApiResponse errorResponse = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    //         return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    // @DeleteMapping("/Remove")
    // public ResponseEntity<?> deleteFile(@RequestParam("filePath") String filePath){
    //     try {
    //         fileService.deleteFile(filePath);
    //         ApiResponse<String> apiResponse = new ApiResponse<>(
    //             HttpStatus.OK.value(),
    //             "File deleted successfully",
    //             null,
    //             LocalDateTime.now());
    //         return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    //     } catch (Exception e) {
    //         System.out.println("Error uploading file: " + e.getMessage());
    //         ApiResponse errorResponse = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    //         return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }
}
