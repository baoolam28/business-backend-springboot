package com.onestep.business_management.DTO.OrderDTO;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderOnlineRequest {

    private UUID userId;
    private Integer addressId;
    private String paymentMethod;
    private List<OrderOnlineDetailRequest> orderOnlineDetailRequests;
}