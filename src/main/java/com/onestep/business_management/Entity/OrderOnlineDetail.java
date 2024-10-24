package com.onestep.business_management.Entity;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orderOnlineDetails")
public class OrderOnlineDetail {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID orderDetailId;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY) // Use LAZY to prevent eager loading issues
    @JoinColumn(name = "orderOnlineId")
    @JsonBackReference // Breaks circular reference during serialization
    private OrderOnline orderOnline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productDetailId")
    private ProductDetail productDetail;

    // Constructor for creating OrderOnlineDetail with specified fields
    public OrderOnlineDetail(int quantity, Double price, ProductDetail productDetail, OrderOnline order) {
        this.quantity = quantity;
        this.price = price;
        this.productDetail = productDetail;
        this.orderOnline = order;
    }

    // Utility method to calculate the total price of this order detail
    public double calculateTotalPrice() {
        return quantity * (price != null ? price : 0); // Ensures price is not null
    }
}
