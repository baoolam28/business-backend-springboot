package com.onestep.business_management.Service.ProductService;


import com.onestep.business_management.DTO.ProductDTO.ProductOnlineRequest;
import com.onestep.business_management.DTO.ProductDTO.ProductOnlineResponse;
import com.onestep.business_management.DTO.ProductDTO.ProdOnlineResponse;
import com.onestep.business_management.DTO.ProductDTO.ProductDetailResponse;
import com.onestep.business_management.DTO.ProductDTO.ProductRequest;
import com.onestep.business_management.DTO.ProductDTO.ProductResponse;
import com.onestep.business_management.DTO.ReviewDTO.ReviewResponse;
import com.onestep.business_management.Entity.*;
import com.onestep.business_management.Exeption.ResourceAlreadyExistsException;
import com.onestep.business_management.Exeption.ResourceNotFoundException;
import com.onestep.business_management.Repository.*;

import com.onestep.business_management.Service.ImageService.ImageService;
import com.onestep.business_management.Service.ReviewSevice.ReviewService;
import com.onestep.business_management.Utils.MapperService;
import com.onestep.business_management.Utils.StringToMapConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private MapperService mapperService;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ImageService imageService;


    public ProductResponse createProduct(ProductRequest productRequest) {

        // Kiểm tra sản phẩm đã tồn tại trong kho
        List<Product> products = productRepository.findProductInStore(
                productRequest.getStoreId(), productRequest.getBarcode());

        if (!products.isEmpty()) {
            throw new ResourceAlreadyExistsException("Product already exists in store: " + productRequest.getStoreId());
        }

        // Ánh xạ yêu cầu sản phẩm thành thực thể
        Product newProduct = ProductMapper.INSTANCE.prodRequestToEntity(productRequest, mapperService);

        // Tạo và thiết lập thông tin hàng tồn kho
        Inventory inventory = new Inventory();
        inventory.setStore(newProduct.getStore());
        inventory.setProduct(newProduct);
        inventory.setQuantityInStock(0);
        inventory.setBarcode(newProduct.getBarcode());
        inventory.setLastUpdated(new Date());

        // Thiết lập hàng tồn kho cho sản phẩm
        newProduct.setInventories(Collections.singletonList(inventory));

        // Lưu sản phẩm mới và trả về phản hồi
        Product savedProduct = productRepository.save(newProduct);
        return ProductMapper.INSTANCE.productToResponse(savedProduct);
    }



    public ProductOnlineResponse createProductOnline(ProductOnlineRequest request){
        Product productOnline = ProductMapper.INSTANCE.ProdOnlineToEntity(request, mapperService);

        Product response = productRepository.save(productOnline);

        return ProductMapper.INSTANCE.productToOnlineResponse(response);
    }

    public ProductOnlineResponse getProductOnlineByStore(Integer prodId){
        return ProductMapper.INSTANCE.productToOnlineResponse(productRepository.findById(prodId).orElseThrow(
                () -> new ResourceNotFoundException("Product with id: "+prodId+" not found!")
        ));
    }


    public Product findById(Integer productId){
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product not found: " + productId));
        return product;
    }

    public List<ProductResponse> getAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper.INSTANCE::productToResponse)
                .collect(Collectors.toList());
    }


    public List<ProductResponse> getAllByStore(UUID storeId) {
        List<Product> products = productRepository.findByStore(storeId);
        return products.stream()
                .map(ProductMapper.INSTANCE::productToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductOnlineResponse> getAllOnlineByStore(UUID storeId) {
        List<Product> products = productRepository.findProductOnlineByStore(storeId);
        return products.stream()
                .map(ProductMapper.INSTANCE::productToOnlineResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getAllOfflineByStore(UUID storeId) {
        List<Product> products = productRepository.findProductOfflineByStore(storeId);
        return products.stream()
                .map(ProductMapper.INSTANCE::productToResponse)
                .collect(Collectors.toList());
    }

    public ProductOnlineResponse getProductDetailOnlineById(Integer productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product with id = "+productId+" not found!")
        );
        ProductOnlineResponse response = ProductMapper.INSTANCE.productToOnlineResponse(product);
        return response;
    }

    public ProductResponse getByBarcode(String barcode) {
        Product product = productRepository.findByBarcode(barcode).orElse(null);

        if (product != null)
            return ProductMapper.INSTANCE.productToResponse(product);

        return null;
    }

    public List<ProductResponse> searchByKeyword(String keyword) {
        List<Product> products = productRepository.searchByKeyword(keyword);
        if (products.isEmpty())
            return null;
        return products.stream()
                .map(ProductMapper.INSTANCE::productToResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse findProductById(Integer productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            return ProductMapper.INSTANCE.productToResponse(product);
        }
        return null;
    }

    public ProductDetailResponse findProductDetailById(Integer productDetailId){
        ProductDetail productDetail = productDetailRepository.findById(productDetailId).orElseThrow(
            () -> new ResourceNotFoundException("Not found product detail by Id: " + productDetailId)
        );

        // Tạo một ProductDetailResponse và chuyển đổi thông tin từ ProductDetail
        ProductDetailResponse response = new ProductDetailResponse();

        // Lấy thông tin từ đối tượng ProductDetail và liên kết với các đối tượng khác
        response.setProductName(productDetail.getProduct().getProductName()); // Lấy tên sản phẩm từ Product
        response.setPrice(productDetail.getPrice()); // Lấy giá từ ProductDetail
        response.setImage(productDetail.getImage()); // Lấy hình ảnh từ ProductDetail

        // Chuyển đổi attributes từ chuỗi JSON thành Map
        if (productDetail.getAttributes() != null) {
            // Sử dụng StringToMapConverter để chuyển đổi chuỗi JSON thành Map
            Map<String, String> attributes = StringToMapConverter.convertStringToMap(productDetail.getAttributes());
            response.setAttributes(attributes);
        }

        return response;
    }

    public List<ProdOnlineResponse> findByCategoryId(int categoryId) {
        // Lấy danh sách sản phẩm theo categoryId
        List<Product> products = productRepository.findByCategoryOnline(categoryId);

        // Kiểm tra xem có sản phẩm nào không
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for category ID: " + categoryId);
        }

        // Chuyển đổi danh sách sản phẩm thành danh sách phản hồi
        return products.stream()
                .map(product -> {
                    // Lấy danh sách review cho mỗi sản phẩm
                    List<Review> reviews = reviewRepository.findByAllReviewByProductDetailId(product.getProductId()).orElse(null);
                    // Sử dụng mapper để chuyển đổi product và reviews sang ProductCategoryResponse
                    return ProductMapper.INSTANCE.productToCategoryResponse(product, reviews);
                })
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findBySupplier(int supplierId) {
        List<Product> products = productRepository.findBySupplierSupplierId(supplierId);
        if (products.isEmpty())
            return null;
        return products.stream()
                .map(ProductMapper.INSTANCE::productToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findByOrigin(int originId) {
        List<Product> products = productRepository.findByOriginOriginId(originId);
        if (products.isEmpty())
            return null;
        return products.stream()
                .map(ProductMapper.INSTANCE::productToResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse updateProduct(ProductRequest productRequest) {
        Product existingProduct = productRepository.findByBarcode(productRequest.getBarcode()).orElse(null);


        if (existingProduct != null) {

            if(productRequest.getImages() != null ){
                List<Image> images = existingProduct.getImages();
                if (images != null && !images.isEmpty()) {
                    System.out.println("delete images: ");
                    imageService.deleteImages(images);
                    images.clear();
                    productRepository.save(existingProduct);  // Save to update image associations
                }
            }

            Product updatedProduct = ProductMapper.INSTANCE.prodRequestToEntity(productRequest, mapperService);
            updatedProduct.setProductId(existingProduct.getProductId());
            if(productRequest.getImages() == null ){
                updatedProduct.setImages(existingProduct.getImages());
            }
            Product savedProduct = productRepository.save(updatedProduct);
            return ProductMapper.INSTANCE.productToResponse(savedProduct);
        }
        return null;
    }

    public ProductResponse deleteProductOffline(Integer productId) {
        Product product = productRepository.findById(productId).orElse(null);
        boolean isDelete = false;
        if (product == null) {
            throw new ResourceNotFoundException("Product not found with id: " + productId);
        }

        boolean hasOrderOfflineDetails = !product.getOrderDetails().isEmpty();
        boolean hasProductDetails = !product.getProductDetails().isEmpty();

        if (hasOrderOfflineDetails || hasProductDetails) {
            // Nếu có phụ thuộc trong `OrderOfflineDetail` hoặc `ProductDetail`, chỉ set `disabled = true`
            product.setDisabled(true);
            productRepository.save(product);
            isDelete = true;
        } else {
            productRepository.delete(product);
            isDelete = true;
        }

        if (isDelete){
            return ProductMapper.INSTANCE.productToResponse(product);
        }

        return null;
    }

    public List<ProdOnlineResponse> getProductsWithReviews(){
        List<Product> products = productRepository.findAllOnline();

        return products.stream()
                .map(product -> {
                    List<ProductDetail> prodDetais = product.getProductDetails();
                    List<Review> reviews = new ArrayList<>();
                    for(ProductDetail detail : prodDetais){
                        reviews = reviewRepository.findByAllReviewByProductDetailId(detail.getProductDetailId()).orElse(null);

                    }
                    return ProductMapper.INSTANCE.productToCategoryResponse(product, reviews);

                })
                .collect(Collectors.toList());
    }

}
