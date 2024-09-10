package com.onestep.business_management.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Products")
public class Product {
    @Id
    private String barcode;

    @Column(name = "productName")
    private String productName;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    private String abbreviations;
    private String unit;
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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;
}
