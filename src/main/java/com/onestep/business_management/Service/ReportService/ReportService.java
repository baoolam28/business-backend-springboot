package com.onestep.business_management.Service.ReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.onestep.business_management.Repository.*;


@Service
public class ReportService {

    @Autowired
    private OrderOfflineRepository orderOfflineRepository;

    // Lấy tổng giá trị đơn hàng theo ngày
    public List<Object[]> getOrderTotalValueByDate(UUID storeId, Date startDate, Date endDate) {
        List<Object[]> result = orderOfflineRepository.getTotalOrderValueByDate(storeId, startDate, endDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Object[] row : result) {
            Date orderDate = (Date) row[1];
            String formattedDate = dateFormat.format(orderDate);
            row[1] = formattedDate;
        }
        return result;
    }

    // Lấy tổng giá trị đơn hàng theo tháng
    public List<Object[]> getOrderTotalValueByMonth(UUID storeId) {
        return orderOfflineRepository.getTotalOrderValueByMonth(storeId);
    }

    // Lấy tổng giá trị đơn hàng theo năm
    public List<Object[]> getOrderTotalValueByYear(UUID storeId) {
        return orderOfflineRepository.getTotalOrderValueByYear(storeId);
    }

    // Lấy tổng giá trị của tất cả đơn hàng
    public Double getTotalOrderValue(UUID storeId) {
        return orderOfflineRepository.getTotalOrderValue(storeId);
    }

    // lay 3 khach hang
    public List<Object[]> getTop3CustomersWithMostOrdersByStore(UUID storeId) {
        return orderOfflineRepository.findTop3CustomersWithMostOrdersByStore(storeId);
    }

    // lay top 3 san pham
    public List<Object[]> getTop3MostSoldProducts(UUID storeId) {
        return orderOfflineRepository.findTop3MostSoldProducts(storeId);
    }

    //lay order theo today
    public List<Object[]> getPaidOrdersMadeTodayByStoreId(UUID storeId) {
        return orderOfflineRepository.findPaidOrdersMadeTodayByStoreId(storeId);
    }

    public List<Object[]> getTotalOrderValueByToday(UUID storeId) {
        return orderOfflineRepository.getTotalOrderValueByToday(storeId);
    }
}
