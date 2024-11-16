package com.onestep.business_management.Service.ImageService;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.onestep.business_management.Entity.Image;
import com.onestep.business_management.Repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    // Upload một hình ảnh
    public Image uploadImage(MultipartFile file) {
        try {
            Map<String, Object> data = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "folder", folderName
            ));
            String imageId = (String) data.get("public_id");
            String imageUrl = (String) data.get("url");

            Image image = new Image();
            image.setImageId(imageId);
            image.setFileName(imageUrl);
            return image;
        } catch (Exception e) {
            // Ghi log hoặc xử lý lỗi một cách hợp lý
            System.err.println("Upload image failed: " + e.getMessage());
            return null; // Trả về null hoặc một giá trị khác để cho biết có lỗi xảy ra
        }
    }

    // Upload nhiều hình ảnh
    public List<Image> uploadImages(List<MultipartFile> files) {
        List<CompletableFuture<Image>> futures = new ArrayList<>();

        for (MultipartFile file : files) {
            // Khởi tạo CompletableFuture cho từng tệp hình ảnh
            CompletableFuture<Image> future = CompletableFuture.supplyAsync(() -> uploadImage(file));
            futures.add(future);
        }

        // Chờ tất cả các tương lai hoàn thành và trả về danh sách hình ảnh đã upload
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        // Chờ tất cả hoàn tất và tạo danh sách các hình ảnh đã upload
        allOf.join(); // Chờ cho tất cả các CompletableFuture hoàn thành

        List<Image> uploadedImages = new ArrayList<>();
        for (CompletableFuture<Image> future : futures) {
            Image image = future.join(); // Chờ cho từng hình ảnh hoàn thành
            if (image != null) {
                uploadedImages.add(image);
            } else {
                // Xử lý hình ảnh không tải lên thành công nếu cần
                System.err.println("Failed to upload image.");
            }
        }

        return uploadedImages; // Trả về danh sách hình ảnh đã upload
    }

    // Xóa hình ảnh
    public void deleteImage(Image image) {
        try {
            cloudinary.uploader().destroy(image.getImageId(), ObjectUtils.emptyMap());
        } catch (Exception e) {
            // Ghi log hoặc xử lý lỗi một cách hợp lý
            System.err.println("Delete image failed: " + e.getMessage());
        }
    }
}
