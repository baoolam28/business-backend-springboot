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

    @ManyToOne
    @JoinColumn(name = "orderOnlineId") 
    @JsonBackReference
    private OrderOnline orderOnline;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productDetailId")
    private ProductDetail productDetail;

    // Phương thức tiện ích để tính toán tổng giá
    public double calculateTotalPrice() {
        return quantity * (price != null ? price : 0); // Đảm bảo giá không null
    }
}
