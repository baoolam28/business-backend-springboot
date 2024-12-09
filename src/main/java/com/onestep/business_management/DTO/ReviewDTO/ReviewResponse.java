package com.onestep.business_management.DTO.ReviewDTO;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.onestep.business_management.Entity.Image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse{
    private Integer reviewId;
    private Integer productDetailId;
    private String productName;
    private String image;
    private String username;
    private Image imageUser;
    private Map<String, String> attributes;
    private Integer rating;
    private String comment;
    private Date reviewDate;
    private List<String> imageUrls; 
    private String videoUrl;   
    private Integer likeCount;
    private Boolean isReviewed;
}
