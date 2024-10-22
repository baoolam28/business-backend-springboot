package com.onestep.business_management.Service.ProductService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onestep.business_management.DTO.ProductDTO.*;
import com.onestep.business_management.Entity.*;
import com.onestep.business_management.Utils.MapperService;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.onestep.business_management.Utils.StringToMapConverter;
import com.onestep.business_management.DTO.ProductDTO.ProductCategoryReponse;
import com.onestep.business_management.DTO.ProductDTO.ProductRequest;
import com.onestep.business_management.DTO.ProductDTO.ProductResponse;
import com.onestep.business_management.Entity.Product;
import com.onestep.business_management.Entity.Review;
import com.onestep.business_management.Utils.MapperService;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    ObjectMapper objectMapper = new ObjectMapper();
    default Product prodRequestToEntity(ProductRequest productRequest, @Context MapperService mapperService) {
        if (productRequest == null) {
            return null;
        }

        Product product = new Product();
        product.setBarcode(productRequest.getBarcode());
        product.setProductName(productRequest.getProductName());
        product.setAbbreviations(productRequest.getAbbreviations());
        product.setUnit(productRequest.getUnit());
        product.setPrice(productRequest.getPrice());
        product.setCreatedDate(productRequest.getCreatedDate());
        product.setCreatedBy(productRequest.getCreatedBy());

        // Fetch related entities
        product.setStore(mapperService.findStoreById(productRequest.getStoreId()));
        product.setCategory(mapperService.findCategoryById(productRequest.getCategoryId()));
        product.setSupplier(mapperService.findSupplierById(productRequest.getSupplierId()));
        product.setOrigin(mapperService.findOriginById(productRequest.getOriginId()));

        return product;
    }

    default ProductResponse productToResponse(Product product) {
        if (product == null) {
            return null;
        }

        ProductResponse productResponse = new ProductResponse();

        // Set basic product fields
        productResponse.setProductId(product.getProductId());
        productResponse.setBarcode(product.getBarcode());
        productResponse.setProductName(product.getProductName());
        productResponse.setAbbreviations(product.getAbbreviations());
        productResponse.setUnit(product.getUnit());

        if (product.getPrice() != null) {
            productResponse.setPrice(product.getPrice());
        }
        

        // Set createdBy, createdDate, and disabled fields
        productResponse.setCreatedBy(product.getCreatedBy());
        productResponse.setCreatedDate(product.getCreatedDate());
        productResponse.setDisabled(product.isDisabled());

        // Set category fields (assuming product.getCategory() returns a Category
        // object)
        if (product.getCategory() != null) {
            productResponse.setCategoryId(product.getCategory().getCategoryId());
            productResponse.setCategoryName(product.getCategory().getCategoryName());
        }

        // Set supplier fields (assuming product.getSupplier() returns a Supplier
        // object)
        if (product.getSupplier() != null) {
            productResponse.setSupplierId(product.getSupplier().getSupplierId());
            productResponse.setSupplierName(product.getSupplier().getSupplierName());
        }

        // Set origin fields (assuming product.getOrigin() returns an Origin object)
        if (product.getOrigin() != null) {
            productResponse.setOriginId(product.getOrigin().getOriginId());
            productResponse.setOriginName(product.getOrigin().getOriginName());
        }

        return productResponse;
    }

    default ProductCategoryReponse productToCategoryResponse(Product product, List<Review> reviews) {
        if (product == null) {
            return null;
        }

        ProductCategoryReponse response = new ProductCategoryReponse();
        response.setProductId(product.getProductId());
        response.setCategoryId(product.getCategory().getCategoryId());
        response.setProductName(product.getProductName());
        response.setPrice(product.getPrice());

        // Tính trung bình rating
        if (reviews != null && !reviews.isEmpty()) {
            double averageRating = reviews.stream()
                    .mapToDouble(Review::getRating)
                    .average()
                    .orElse(0.0); // Nếu không có đánh giá, trả về 0
            response.setRating(averageRating);
            response.setTotalReviews(reviews.size());
        } else {
            response.setRating(0.0); // Không có đánh giá, rating mặc định là 0
            response.setTotalReviews(0);
        }

        // Thông tin cửa hàng
        if (product.getStore() != null) {
            response.setStoreName(product.getStore().getStoreName());
            response.setPickupAddress(product.getStore().getPickupAddress());
        }

        // Thông tin danh mục
        if (product.getCategory() != null) {
            response.setCategoryName(product.getCategory().getCategoryName());
        }

        return response;
    }


    default Product ProdOnlineToEntity(ProductOnlineRequest prodRequest, @Context MapperService mapperService){

        if (prodRequest == null) {
            return null;
        }

        try {
            Product product = new Product();
            product.setProductName(prodRequest.getProductName());
            product.setDescription(prodRequest.getDescription());
            product.setPrice(prodRequest.getPrice());
            product.setOnline(true);
            Category category = mapperService.findCategoryById(prodRequest.getCategoryId());
            product.setCategory(category);
            Store store = mapperService.findStoreById(prodRequest.getStoreId());
            product.setStore(store);
            product.setImages(mapperService.uploadImages(prodRequest.getImages()));

            List<ProductDetail> productDetails = prodRequest.getProductDetail().stream()
                    .map(detailRequest -> {
                        ProductDetail productDetail = new ProductDetail();
                        productDetail.setPrice(detailRequest.getPrice());
                        productDetail.setSku(detailRequest.getSku());
                        productDetail.setQuantityInStock(detailRequest.getQuantityInStock());
                        Image image = mapperService.uploadImage(detailRequest.getImage());
                        productDetail.setImage(image.getFileName());
                        productDetail.setHeight(detailRequest.getHeight());
                        productDetail.setLength(productDetail.getLength());
                        productDetail.setWidth(productDetail.getWidth());
                        productDetail.setWeight(productDetail.getWeight());

                        productDetail.setProduct(product);
                        Map<String, String> attributes = detailRequest.getAttributes();
                        String attributesJson = null;
                        try {
                            attributesJson = objectMapper.writeValueAsString(attributes);
                        } catch (JsonProcessingException e) {
                            System.out.println("Lõi chet con di me may");
                            throw new RuntimeException(e);
                        }
                        productDetail.setAttributes(attributesJson);

                        return productDetail;
                    })
                    .collect(Collectors.toList());

            System.out.println(productDetails.toString());
            product.setProductDetails(productDetails);

            return product;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    private Variants productDetailToVariants(ProductDetail productDetail) {
        if (productDetail == null) {
            return null;
        }

        Variants variant = new Variants();
        variant.setProductDetailId(productDetail.getProductDetailId());
        variant.setPrice(productDetail.getPrice());
        variant.setQuantityInStock(productDetail.getQuantityInStock());
        variant.setSku(productDetail.getSku());
        variant.setAttributes(StringToMapConverter.convertStringToMap(productDetail.getAttributes()));
        variant.setImage(productDetail.getImage());
        variant.setHeight(productDetail.getHeight());
        variant.setLength(productDetail.getLength());
        variant.setWidth(productDetail.getWidth());
        variant.setWeight(productDetail.getWeight());

        return variant;

    }

    default ProductOnlineResponse productToOnlineResponse(Product product){
        if (product == null) {
            return null;
        }

        ProductOnlineResponse response = new ProductOnlineResponse();
        response.setProductId(product.getProductId());
        response.setDescription(product.getDescription());
        response.setProductName(product.getProductName());
        response.setPrice(product.getPrice());
        Store store = product.getStore();
        response.setStoreId(store.getStoreId());
        response.setStoreName(store.getStoreName());
        Category category = product.getCategory();
        response.setCategoryName(category.getCategoryName());
        response.setImages(
                product.getImages().stream()
                        .map(image -> image.getFileName())
                        .collect(Collectors.toList()));
        response.setDistrict(store.getDistrict());

        List<Variants> variants = product.getProductDetails().stream()
                .map(this::productDetailToVariants)
                .collect(Collectors.toList());

        response.setVariants(variants);

        return response;
    }

}
