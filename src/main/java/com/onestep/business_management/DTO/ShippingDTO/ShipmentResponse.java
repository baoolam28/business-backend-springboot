package com.onestep.business_management.DTO.ShippingDTO;

import java.util.List;

import com.onestep.business_management.DTO.OrderDTO.OrderOnlineDetailResponse;
import com.onestep.business_management.Entity.Shipment.ShippingStatus;


public class ShipmentResponse {

    private Integer addressId;
    private Integer storeId;
    private ShippingStatus shippingStatus;
    private List<OrderOnlineDetailResponse> orderOnlineDetails;
}