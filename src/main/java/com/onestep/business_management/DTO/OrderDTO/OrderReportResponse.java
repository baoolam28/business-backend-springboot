package com.onestep.business_management.DTO.OrderDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderReportResponse {
    private long totalOrders;
    double totalRevenue;
    double averageOrderValue;
    private Map<String, Integer> ordersByWeek;
    private Map<String, Integer> ordersByMoth;
    private Map<String, Integer> ordersByYear;
}
