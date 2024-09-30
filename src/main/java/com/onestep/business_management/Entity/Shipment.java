package com.onestep.business_management.Entity;

import java.util.Date;

import jakarta.persistence.*;
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
    private Integer shipmentId;

    @OneToOne
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    private OrderOnline orderOnline;

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private ShippingAddress shippingAddress;

    @Enumerated(EnumType.ORDINAL)
    private ShippingStatus shippingStatus;

    @Column(name = "carrier", length = 50, nullable = true, columnDefinition = "NVARCHAR(50)")
    private String carrier;

    @Column(name = "trackingNumber", length = 50, nullable = true)
    private String trackingNumber;

    @Temporal(TemporalType.TIMESTAMP)
    private Date shippedDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expectedDeliverDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveredDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createAt", updatable = false)
    private Date createAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateAt")
    private Date updateAt;

    @PrePersist
    protected void onCreate() {
        this.createAt = new Date();
        this.updateAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateAt = new Date();
    }

    public enum ShippingStatus {
        CHO_XAC_NHAN(0),
        DA_DONG_GOI(1),
        DANG_GIAO_HANG(2),
        GIAO_HANG_THANH_CONG(3),
        DA_HUY_DON(4),
        GIAO_HANG_THAT_BAI(5);

        private final int value;

        ShippingStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static ShippingStatus fromValue(int value) {
            for (ShippingStatus status : ShippingStatus.values()) {
                if (status.getValue() == value) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Invalid shipping status value: " + value);
        }
    }
}
