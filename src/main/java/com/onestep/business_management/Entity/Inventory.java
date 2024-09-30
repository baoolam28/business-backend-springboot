package com.onestep.business_management.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inventoryId;

    @ManyToOne
    @JoinColumn(name = "storeId", nullable = false)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @Column(name = "barcode", length = 100, nullable = false) // Cập nhật chiều dài và nullable
    private String barcode;

    @Column(name = "quantityInStock")
    private int quantityInStock;

    @Column(name = "lastUpdated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;

    // Phương thức tiện ích có thể thêm vào
    public String getInventoryDetails() {
        return String.format("Inventory [ID=%d, Product=%s, Quantity=%d, LastUpdated=%s]",
                inventoryId, product.getProductName(), quantityInStock, lastUpdated);
    }
}
