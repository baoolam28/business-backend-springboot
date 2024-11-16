package com.onestep.business_management.DTO.OrderReportDTO;

// OrderReportResponse.java
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderReportResponse {
    private int totalOfflineOrders;
    private double totalOfflineRevenue;
    private int totalOnlineOrders;
    private double totalOnlineRevenue;
    private double averageOfflineOrderValue;
    private double averageOnlineOrderValue;
    private Map<String, Integer> customerCountByWeek;
    private Map<String, Integer> customerCountByMonth;
    private Map<String, Integer> customerCountByYear;

}
