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
public class OrderOnlineResponse {
    private UUID orderId;
    private UUID userId;
    private String userName; 
    private String imageName ; 
    private String phone ;
    private String address ; 
    private String receiver ; 
    private String district ;
    private String province ; 
    private Date orderDate;
    private String status;
    private boolean paymentStatus;
    private String paymentMethod;
    private UUID storeId;
    private String storeName;
    private List<OrderOnlineDetailResponse> orderDetails;
}   