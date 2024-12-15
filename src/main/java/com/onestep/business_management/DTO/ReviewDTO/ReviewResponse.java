package com.onestep.business_management.DTO.ReviewDTO;


import java.util.Date;
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
    private String productImage;
    private String username;
    private String fullName;
    private String imageUser;
    private Map<String, String> attributes;
    private Integer rating;
    private String comment;
    private Date reviewDate;
    private List<String> images;   
    private Integer likeCount;
    private Boolean isReviewed;
}
