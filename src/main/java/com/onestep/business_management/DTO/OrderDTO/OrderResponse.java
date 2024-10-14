package com.onestep.business_management.DTO.OrderDTO;

import com.onestep.business_management.Entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private UUID orderId;
    private Date orderDate;
    private String status;
    private Integer customerId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String customerAddress;
    private boolean paymentStatus;
    private String paymentMethod;
    private List<OrderDetailResponse> orderDetails;
}

