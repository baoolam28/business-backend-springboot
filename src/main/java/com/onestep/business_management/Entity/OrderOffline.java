package com.onestep.business_management.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "OrdersOffline")
public class OrderOffline {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "orderOfflineId")
    private UUID orderOfflineId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Column(length = 20) // Thêm chiều dài cho status
    private String status;

    private boolean paymentStatus;

    @Column(length = 20) // Thêm chiều dài cho paymentMethod
    private String paymentMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId", nullable = true)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId", nullable = true)
    private Store store;

    // Sửa 'OrderOffline' thành 'orderOffline'
    @OneToMany(mappedBy = "orderOffline", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderOfflineDetail> orderDetails = new ArrayList<>();

    // Phương thức tiện ích có thể thêm vào
    public String getOrderSummary() {
        return String.format("Order [ID=%s, Date=%s, Status=%s, PaymentStatus=%s]",
                orderOfflineId, orderDate, status, paymentStatus);
    }
}
