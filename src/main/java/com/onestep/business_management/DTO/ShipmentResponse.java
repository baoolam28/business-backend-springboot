package com.onestep.business_management.DTO;

import java.util.List;

import com.onestep.business_management.Entity.Shipment.shippingStatus;

public class ShipmentResponse {

    private Integer addressId;
    private Integer storeId;
    private shippingStatus shippingStatus;
    private List<OrderOnlineDetailResponse> orderOnlineDetails;
}