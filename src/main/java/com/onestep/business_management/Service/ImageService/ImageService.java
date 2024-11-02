package com.onestep.business_management.Service.ImageService;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.onestep.business_management.Entity.Image;
import com.onestep.business_management.Repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class ImageService {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private ImageRepository imageRepository;
    private String folderName = "ecommerce_images";

    @Async
    public CompletableFuture<Image> uploadImage(MultipartFile file) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Map<String, Object> data = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                        "folder", folderName
                ));
                String imageId = (String) data.get("public_id");
                String imageName = (String) data.get("url");

                Image image = new Image();
                image.setImageId(imageId);
                image.setFileName(imageName);
                return image;
            } catch (Exception e) {
                throw new IllegalArgumentException("Upload image failed: " + e.getMessage());
            }
        });
    }

    @Async
    public CompletableFuture<List<Image>> uploadImages(List<MultipartFile> files) {
        return CompletableFuture.supplyAsync(() -> {
            List<Image> uploadedImages = new ArrayList<>();
            try {
                for (MultipartFile file : files) {
                    Map<String, Object> data = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                            "folder", folderName
                    ));
                    String imageId = (String) data.get("public_id");
                    String imageName = (String) data.get("url");

                    Image image = new Image();
                    image.setImageId(imageId);
                    image.setFileName(imageName);
                    uploadedImages.add(image);
                }
                return uploadedImages;  
            } catch (Exception e) {
                throw new IllegalArgumentException("Upload images failed: " + e.getMessage());
            }
        });
    }

    @Async
    public CompletableFuture<Void> deleteImage(Image image) {
        return CompletableFuture.runAsync(() -> {
            try {
                cloudinary.uploader().destroy(image.getImageId(), Map.of());
            } catch (Exception e) {
                throw new IllegalArgumentException("Delete image failed: " + e.getMessage());
            }
        });
    }
}
