package com.onestep.business_management.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
    @Column(name = "OrderOnlineId")
    private UUID OrderOnlineId;

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

    @OneToMany(mappedBy = "orderOnline", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderOnlineDetail> orderDetails = new ArrayList<>();

    // Phương thức tiện ích để lấy thông tin đơn hàng
    public String getOrderSummary() {
        return String.format("OrderOnline [ID=%s, Date=%s, Status=%s]", OrderOnlineId, orderDate, status);
    }
}
