package com.onestep.business_management.Repository;

import com.onestep.business_management.Entity.Product;
import com.onestep.business_management.Entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByBarcode(String barcode);
    @Query("SELECT p FROM Product p WHERE " +
            "p.productName LIKE %:keyword% OR " +
            "p.abbreviations LIKE %:keyword% OR " +
            "p.unit LIKE %:keyword% OR " +
            "p.price LIKE %:keyword% OR " +
            "p.origin.originName LIKE %:keyword% OR " +
            "p.category.categoryName LIKE %:keyword%")
    List<Product> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT p FROM Product p WHERE p.store.storeId = :storeId AND p.barcode = :barcode")
    List<Product> findProductInStore(@Param("storeId") UUID storeId, @Param("barcode") String barcode);

    List<Product> findByCategoryCategoryId(Integer categoryId);
    List<Product> findBySupplierSupplierId(Integer supplierId);
    List<Product> findByOriginOriginId(Integer originId);

    @Query("SELECT p FROM Product p WHERE p.store.storeId = :storeId")
    List<Product> findByStore(@Param("storeId") UUID storeId);
}
