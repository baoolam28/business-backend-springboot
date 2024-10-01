package com.onestep.business_management.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Document_Detail")
public class DocumentDetail {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID docDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docId", nullable = false)
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    private int quantity;

    private float price;

    @Column(name = "totalPrice")
    private float totalPrice;

    @Column(name = "createdDate")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;

    // Phương thức để tính toán giá tổng
    public void calculateTotalPrice() {
        this.totalPrice = this.quantity * this.price;
    }
}
