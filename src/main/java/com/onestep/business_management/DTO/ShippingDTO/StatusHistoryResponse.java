package com.onestep.business_management.DTO.ShippingDTO;

import java.util.Date;

import com.onestep.business_management.Entity.Shipment.ShippingStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusHistoryResponse {
    private ShippingStatus shippingStatus;
    private Date time;
}
