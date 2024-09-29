package com.onestep.business_management.DTO;

import java.util.List;

import com.onestep.business_management.Entity.Shipment.shippingStatus;

public class ShipmentRequest {

    private Integer addressId;
    private shippingStatus shippingStatus;
    private List<OrderOnlineDetailRequest> orderOnlineDetailRequests;
}