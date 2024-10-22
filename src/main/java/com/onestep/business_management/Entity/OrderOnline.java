package com.onestep.business_management.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "ordersOnline")
public class OrderOnline {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "orderOnlineId")
    private UUID orderOnlineId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    public enum Status {
        CHUA_GIAO_HANG,
        DANG_GIAO_HANG,
        CHO_XAC_NHAN,
        DANG_DONG_GOI,
        GIAO_HANG_THANH_CONG
    }

    private Status status;

    private boolean paymentStatus;

    private String paymentMethod;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "userId", nullable = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "storeId", nullable = true) // Mỗi đơn hàng gắn với 1 cửa hàng
    private Store store;

    @OneToMany(mappedBy = "orderOnline", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OrderOnlineDetail> orderDetails = new ArrayList<>();

    @OneToOne(mappedBy = "orderOnline", cascade = CascadeType.ALL)
    @JsonIgnore
    private Shipment shipment;

    // Utility method for order summary
    public String getOrderSummary() {
        return String.format("OrderOnline [ID=%s, Date=%s, Status=%s]", orderOnlineId, orderDate, status);
    }
}

