package com.onestep.business_management.DTO.OrderDTO;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderShippingRespons {
    private UUID orderOnlineId;
    private Date orderDate;
    private String paymentMethod;
    private String paymentStatus;
    private String status;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String wardCode;
    private String district;
    private String province;
    private String shippingStatus;
    private Double shippingFee;
    private String shippingMethod;
    private String note;
    private String trackingNumber;
    private Date deliveredDate;
    private Date expectedDeliverDate;
}
