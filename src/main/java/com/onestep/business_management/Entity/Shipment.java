package com.onestep.business_management.Entity;



import java.util.Date;

import jakarta.persistence.Column;
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
    private Integer shipmentId;

    @OneToOne
    @JoinColumn(name = "orderId", nullable = false)
    private OrderOnline orderOnline;

    @OneToOne
    @JoinColumn(name = "addressId", nullable = false)
    private ShippingAddress shippingAddress;

    private ShippingStatus shippingStatus;

    @Column(name = "carrier", length = 50, nullable = true, columnDefinition = "NVARCHAR(50)")
    private String carrier;
    private int trackingNumber;
    private Date shippedDate;
    private Date expectedDeliverDate;
    private Date deliveredDate;
    private Date createAt;
    private Date updateAt;   


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



