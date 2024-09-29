package com.onestep.business_management.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderOnlineRequest {

    private Integer addressId;
    private String paymentMethod;
    private List<OrderOnlineDetailRequest> orderOnlineDetailRequests;
}