package com.onestep.business_management.DTO.ShippingDTO;

import java.util.List;

import com.onestep.business_management.DTO.OrderDTO.OrderOnlineDetailRequest;
import com.onestep.business_management.Entity.Shipment.ShippingStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentRequest {

    private Integer addressId;
    private ShippingStatus shippingStatus ;
    private List<OrderOnlineDetailRequest> orderOnlineDetailRequests;
}