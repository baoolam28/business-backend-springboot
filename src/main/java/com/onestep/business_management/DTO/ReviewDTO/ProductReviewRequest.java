package com.onestep.business_management.DTO.ReviewDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductReviewRequest {
    private Integer rating;
    private Integer productId;
}
