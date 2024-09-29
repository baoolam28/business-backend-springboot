package com.onestep.business_management.Entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "shipments")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shipmetId;

    @OneToOne
    @JoinColumn(name = "orderId", nullable = false)
    private Integer orderId;

    @OneToOne
    @JoinColumn(name = "addressId", nullable = false)
    private Integer addressId;

    private boolean shippingMethod;

    public enum shippingStatus {
        CHUA_GIAO_HANG,
        DANG_GIAO_HANG,
        CHO_XAC_NHAN,
        DANG_DONG_GOI,
        GIAO_HANG_THANH_CONG
    }

    private shippingStatus shippingStatus;
    private Date estimatedDelivery;
    private int trackingNumber;
}