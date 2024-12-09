package com.onestep.business_management.Controller;

import com.onestep.business_management.DTO.API.ApiResponse;
import com.onestep.business_management.DTO.ImageDTO.ImageRequest;
import com.onestep.business_management.DTO.ImageDTO.ImageResponse;
import com.onestep.business_management.DTO.ProductDTO.ProductOnlineRequest;
import com.onestep.business_management.DTO.ProductDTO.ProductResponse;
import com.onestep.business_management.Entity.Image;
import com.onestep.business_management.Service.ImageService.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/api/images")
public class ImageUploadController {

    @Autowired
    private ImageService imageService;

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse<?>> uploadImage(@ModelAttribute ImageRequest imageReq) {
        ImageResponse imageRes = new ImageResponse();
        Image image = imageService.uploadImage(imageReq.getImage());
        if(image == null){
            ApiResponse<ImageResponse> apiResponse = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Upload image failed!",
                    null,
                    LocalDateTime.now()
            );
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        imageRes.setImageId(image.getImageId());
        imageRes.setImageUrl(image.getFileName());
        ApiResponse<ImageResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Upload image sucessfully!",
                imageRes,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
