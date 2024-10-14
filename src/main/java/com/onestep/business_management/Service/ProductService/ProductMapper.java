package com.onestep.business_management.Service.ProductService;

import com.onestep.business_management.DTO.ProductDTO.ProductRequest;
import com.onestep.business_management.DTO.ProductDTO.ProductResponse;
import com.onestep.business_management.Entity.Product;
import com.onestep.business_management.Utils.MapperService;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import java.util.UUID;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

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

        // Giả sử product.getStore().getStoreId() trả về UUID
        UUID storeId = product.getStore().getStoreId(); // Lấy UUID
        // Chuyển đổi UUID thành String
        String storeIdString = storeId.toString();
        productResponse.setStoreId(storeIdString);
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
}
