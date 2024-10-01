package com.onestep.business_management.DTO.ShippingDTO;

import java.util.List;

import com.onestep.business_management.DTO.OrderDTO.OrderOnlineDetailRequest;
import com.onestep.business_management.Entity.Shipment.ShippingStatus;


public class ShipmentRequest {

    private Integer addressId;
    private ShippingStatus shippingStatus ;
    private List<OrderOnlineDetailRequest> orderOnlineDetailRequests;
}