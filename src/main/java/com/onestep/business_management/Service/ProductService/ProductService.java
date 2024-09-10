package com.onestep.business_management.Service.ProductService;

import com.onestep.business_management.DTO.ProductRequest;
import com.onestep.business_management.DTO.ProductResponse;
import com.onestep.business_management.Entity.Product;
import com.onestep.business_management.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product newProduct = ProductMapper.INSTANCE.toEntity(productRequest);
        newProduct.setCreatedDate(new Date());
        newProduct.setDisabled(false);
        Product savedProduct = productRepository.save(newProduct);
        return ProductMapper.INSTANCE.toResponse(savedProduct);
    }

    public List<ProductResponse> getAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse getByBarcode(String barcode) {
        Product product = productRepository.findByBarcode(barcode).orElse(null);

        if (product != null) return ProductMapper.INSTANCE.toResponse(product);

        return null;
    }

    public List<ProductResponse> searchByKeyword(String keyword) {
        List<Product> products = productRepository.searchByKeyword(keyword);
        if(products.isEmpty()) return null;
        return products.stream()
                .map(ProductMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findByCategory(int categoryId) {
        List<Product> products = productRepository.findByCategoryCategoryId(categoryId);
        if(products.isEmpty()) return null;
        return products.stream()
                .map(ProductMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findBySupplier(int supplierId) {
        List<Product> products = productRepository.findBySupplierSupplierId(supplierId);
        if(products.isEmpty()) return null;
        return products.stream()
                .map(ProductMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findByOrigin(int originId) {
        List<Product> products = productRepository.findByOriginOriginId(originId);
        if(products.isEmpty()) return null;
        return products.stream()
                .map(ProductMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse updateProduct(ProductRequest productRequest) {
        Product existingProduct = productRepository.findByBarcode(productRequest.getBarcode()).orElse(null);
        if (existingProduct != null) {
            Product updatedProduct = ProductMapper.INSTANCE.toEntity(productRequest);
            Product savedProduct = productRepository.save(updatedProduct);
            return ProductMapper.INSTANCE.toResponse(savedProduct);
        }
        return null;
    }

    public ProductResponse deleteProduct(String barcode) {
        Product deleteProduct = productRepository.findByBarcode(barcode).orElse(null);
        if(deleteProduct != null) {
            productRepository.delete(deleteProduct);
            return ProductMapper.INSTANCE.toResponse(deleteProduct);
        }
        return null;
    }
}
