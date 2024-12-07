package com.onestep.business_management.DTO.ReviewDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductReviewResponse {
    private Integer totalReview;
    private List<ReviewResponse> review;
}
