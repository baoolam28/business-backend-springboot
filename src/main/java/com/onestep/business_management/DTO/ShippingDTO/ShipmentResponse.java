package com.onestep.business_management.DTO.ShippingDTO;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.onestep.business_management.DTO.OrderDTO.OrderOnlineDetailResponse;
import com.onestep.business_management.Entity.Shipment.ShippingStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentResponse {
    private Integer shipmentId;
    private UUID userId;
    private Integer addressId;
    private Date orderDate;
    private UUID storeId;
    private String storeName;
    private ShippingStatus shippingStatus;
    private List<OrderOnlineDetailResponse> orderOnlineDetails;
}