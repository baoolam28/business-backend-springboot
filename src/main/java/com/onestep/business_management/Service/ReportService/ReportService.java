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
import java.util.stream.Collectors;

import com.onestep.business_management.DTO.OrderDTO.OrderDetailResponse;
import com.onestep.business_management.DTO.OrderDTO.OrderOfflineDetailResponse;
import com.onestep.business_management.Entity.OrderOffline;
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
        return orderOfflineRepository.getOrdersWithPriceByToday(storeId);
    }

    public List<Object[]> getAllOrderByStoreId(UUID storeId) {
        return orderOfflineRepository.findAllOrdersByStoreId(storeId);
    }

    public List<OrderOfflineDetailResponse> getOrdersByStoreId(UUID storeId) {
        List<OrderOffline> orders = orderOfflineRepository.findOrdersByStoreId(storeId);

        return orders.stream().map(order -> {
            OrderOfflineDetailResponse response = new OrderOfflineDetailResponse();
            response.setOrderId(order.getOrderOfflineId());
            response.setOrderDate(order.getOrderDate());
            response.setStatus(order.getStatus());
            response.setCustomerId(order.getCustomer().getCustomerId());
            response.setCustomerName(order.getCustomer().getName());
            response.setCustomerPhone(order.getCustomer().getPhone());
            response.setPaymentMethod(order.getPaymentMethod());
            response.setStoreId(order.getStore().getStoreId());

            // Map OrderDetails to OrderDetailResponse
            List<OrderDetailResponse> orderDetails = order.getOrderDetails().stream().map(orderDetail -> {
                OrderDetailResponse detailResponse = new OrderDetailResponse();
                detailResponse.setOrderDetailId(orderDetail.getOrderDetailId());
                detailResponse.setProductId(orderDetail.getProduct().getProductId());
                detailResponse.setName(orderDetail.getProduct().getProductName());
                detailResponse.setBarcode(orderDetail.getProduct().getBarcode());
                detailResponse.setQuantity(orderDetail.getQuantity());
                detailResponse.setPrice(orderDetail.getPrice());
                // Removed images mapping
                return detailResponse;
            }).collect(Collectors.toList());

            response.setOrderDetails(orderDetails);
            return response;
        }).collect(Collectors.toList());
    }
}
