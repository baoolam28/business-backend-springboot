package com.onestep.business_management.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    private String barcode;

    @Column(name = "productName", length = 100, nullable = true, columnDefinition = "NVARCHAR(100)")
    private String productName;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @Column(name = "abbreviations", length = 50, nullable = true, columnDefinition = "NVARCHAR(50)")
    private String abbreviations;
    @Column(name = "unit", length = 20, nullable = true, columnDefinition = "NVARCHAR(20)")
    private String unit;
    @Column(name = "price", precision = 12, scale = 2, nullable = false)
    private Float price;

    @ManyToOne
    @JoinColumn(name = "supplierId")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name= "originId")
    private Origin origin;

    @Column(name = "createdDate")
    private Date createdDate;

    @Column(name = "createdBy")
    private Integer createdBy;

    private boolean disabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId", nullable = false)
    private Store store;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderOfflineDetail> orderDetails;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();
}
