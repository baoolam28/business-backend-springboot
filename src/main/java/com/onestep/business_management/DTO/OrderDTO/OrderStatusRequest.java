package com.onestep.business_management.DTO.OrderDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.onestep.business_management.Entity.OrderOnline;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusRequest {
    private OrderOnline.Status status;
}
