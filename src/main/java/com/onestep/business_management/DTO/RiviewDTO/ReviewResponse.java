package com.onestep.business_management.DTO.RiviewDTO;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse{
    private String productName;
    private String fullName;
    private String imageUser;
    private Integer rating;
    private String comment;
    private Date reviewDate;
}
