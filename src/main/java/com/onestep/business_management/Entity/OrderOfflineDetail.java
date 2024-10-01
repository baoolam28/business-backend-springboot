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
@Table(name = "OrderOfflineDetails")
public class OrderOfflineDetail {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "orderDetailId")
    private UUID orderDetailId;

    @Column(nullable = false, length = 50) 
    private String barcode;

    @Column(nullable = false) 
    private int quantity;

    @Column(nullable = false) 
    private double price;

    @ManyToOne
    @JoinColumn(name = "orderOfflineId") 
    @JsonBackReference
    private OrderOffline orderOffline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    public String getDetailSummary() {
        return String.format("OrderDetail [ID=%d, Barcode=%s, Quantity=%d, Price=%.2f]",
                orderDetailId, barcode, quantity, price);
    }
}
