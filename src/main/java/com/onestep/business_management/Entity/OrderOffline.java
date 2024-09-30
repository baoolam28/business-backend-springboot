package com.onestep.business_management.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.ArrayList;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class OrderOffline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID orderId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    private String status;
    private boolean paymentStatus;
    private String paymentMethod;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "customerId", nullable = true)
    private Customer customer;

    @OneToMany(mappedBy = "orderDetailId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderOfflineDetail> orderDetails = new ArrayList<>();
}
