package com.onestep.business_management.DTO.RiviewDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ReviewRequest {
    private String productId;
    private Integer userId;
    private Integer rating;
    private String comment;
}
