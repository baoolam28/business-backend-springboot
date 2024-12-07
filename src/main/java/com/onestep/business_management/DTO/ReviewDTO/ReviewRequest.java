package com.onestep.business_management.DTO.ReviewDTO;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor

public class ReviewRequest {
    private Integer reviewId;
    private Integer productDetailId;    
    private UUID userId;
    private Integer rating;
    private String comment;
    private List<String> imageUrls;  
    private String videoUrl; 
}
