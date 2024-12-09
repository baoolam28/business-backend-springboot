package com.onestep.business_management.DTO.ImageDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponse {
    private String imageId;
    private String imageUrl;
}
