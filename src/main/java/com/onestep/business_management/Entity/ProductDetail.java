package com.onestep.business_management.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productDetailId;

    @Column(name = "price")
    private Double price;

    private String sku;

    private Integer quantityInStock;

    private String image;

    private Double height;

    private Double length;

    private Double width;

    private Double weight;

    @Column(name = "attributes", length = 255, nullable = true, columnDefinition = "NVARCHAR(255)")
    private String attributes;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "productId")
    private Product product;



}
