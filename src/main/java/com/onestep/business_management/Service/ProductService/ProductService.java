package com.onestep.business_management.Service.ProductService;


import com.onestep.business_management.DTO.ProductDTO.ProductOnlineRequest;
import com.onestep.business_management.DTO.ProductDTO.ProductOnlineResponse;
import com.onestep.business_management.DTO.ProductDTO.ProductCategoryReponse;
import com.onestep.business_management.DTO.ProductDTO.ProductRequest;
import com.onestep.business_management.DTO.ProductDTO.ProductResponse;
import com.onestep.business_management.Entity.Product;
import com.onestep.business_management.Entity.Review;
import com.onestep.business_management.Entity.Store;
import com.onestep.business_management.Entity.User;
import com.onestep.business_management.Exeption.ResourceAlreadyExistsException;
import com.onestep.business_management.Exeption.ResourceNotFoundException;
import com.onestep.business_management.Repository.ProductRepository;
import com.onestep.business_management.Repository.ReviewRepository;
import com.onestep.business_management.Repository.StoreRepository;
import com.onestep.business_management.Repository.UserRepository;

import com.onestep.business_management.Utils.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private MapperService mapperService;

    @Autowired ReviewRepository reviewRepository ;

    public ProductResponse createProduct(ProductRequest productRequest) {

        List<Product> products = productRepository.findProductInStore(
                productRequest.getStoreId(), productRequest.getBarcode());

        if (!products.isEmpty()) {
            throw new ResourceAlreadyExistsException("Product already exist in store :" + productRequest.getStoreId());
        }

        Product newProduct = ProductMapper.INSTANCE.prodRequestToEntity(productRequest, mapperService);
        Product response = productRepository.save(newProduct);
        return ProductMapper.INSTANCE.productToResponse(response);
    }

    public ProductOnlineResponse createProductOnline(ProductOnlineRequest request){
        Product productOnline = ProductMapper.INSTANCE.ProdOnlineToEntity(request, mapperService);

        Product response = productRepository.save(productOnline);

        return ProductMapper.INSTANCE.productToOnlineResponse(response);
    }

    public ProductOnlineResponse findProductOnline(Integer prodId){
        return ProductMapper.INSTANCE.productToOnlineResponse(productRepository.findById(prodId).orElseThrow(
                () -> new ResourceNotFoundException("Product with id: "+prodId+" not found!")
        ));
    }


    public Product findById(Integer productId){
        Product product = productRepository.findById(productId).orElseThrow(
            () -> new ResourceNotFoundException("Product not found: " + productId)
        );
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

    public List<ProductCategoryReponse> findByCategoryId(int categoryId) {
        // Lấy danh sách sản phẩm theo categoryId
        List<Product> products = productRepository.findByCategoryCategoryId(categoryId);

        // Kiểm tra xem có sản phẩm nào không
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for category ID: " + categoryId);
        }

        // Chuyển đổi danh sách sản phẩm thành danh sách phản hồi
        return products.stream()
                .map(product -> {
                    // Lấy danh sách review cho mỗi sản phẩm
                    List<Review> reviews = reviewRepository.findByProductProductId(product.getProductId());

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
            Product updatedProduct = ProductMapper.INSTANCE.prodRequestToEntity(productRequest, mapperService);
            Product savedProduct = productRepository.save(updatedProduct);
            return ProductMapper.INSTANCE.productToResponse(savedProduct);
        }
        return null;
    }

    public ProductResponse deleteProduct(String barcode) {
        Product deleteProduct = productRepository.findByBarcode(barcode).orElse(null);
        if (deleteProduct != null) {
            productRepository.delete(deleteProduct);
            return ProductMapper.INSTANCE.productToResponse(deleteProduct);
        }
        return null;
    }

}
