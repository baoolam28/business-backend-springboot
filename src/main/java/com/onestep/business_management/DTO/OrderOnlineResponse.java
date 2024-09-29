package com.onestep.business_management.DTO;

import com.onestep.business_management.Entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderOnlineResponse {

    private int orderId;
    private Date orderDate;
    private String status;
    private Customer customer;
    private boolean paymentStatus;
    private String paymentMethod;
    private List<OrderOnlineDetailResponse> orderOnlineDetails;
}