package com.onestep.business_management.DTO.OrderDTO;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderOfflineDetailResponse {
    
    private UUID orderId;
    private Date orderDate;
    private String status;
    private Integer customerId;
    private String customerName;
    private String customerPhone;
    private String paymentMethod;
    private UUID storeId;
    private List<OrderDetailResponse> orderDetails;

}

