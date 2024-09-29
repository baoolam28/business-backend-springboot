package com.onestep.business_management.Service.OrderService;

import com.onestep.business_management.DTO.OrderReportResponse;
import com.onestep.business_management.DTO.OrderRequest;
import com.onestep.business_management.DTO.OrderResponse;
import com.onestep.business_management.Entity.Order;
import com.onestep.business_management.Entity.OrderDetail;
import com.onestep.business_management.Entity.Customer;
import com.onestep.business_management.Entity.Product;
import com.onestep.business_management.Repository.OrderDetailRepository;
import com.onestep.business_management.Repository.OrderRepository;
import com.onestep.business_management.Repository.CustomerRepository;
import com.onestep.business_management.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public OrderResponse createOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setStatus("PENDING"); // Order is pending payment
        order.setPaymentStatus(false); // Payment status is initially unpaid
        order.setPaymentMethod(null); // No payment method initially

        // Find or create the customer
        Customer existingCustomer = customerRepository.findByCustomerId(orderRequest.getCustomerId()).orElse(null);

        if (existingCustomer == null) {
            new RuntimeException("Customer not found");
        }
        
        order.setCustomer(existingCustomer);

        // Map OrderRequest to OrderDetail
        List<OrderDetail> orderDetails = orderRequest.getOrderDetails().stream().map(request -> {
            OrderDetail detail = new OrderDetail();
            detail.setQuantity(request.getQuantity());
            detail.setPrice(request.getPrice());

            Product product = productRepository.findByBarcode(request.getBarcode())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            detail.setProduct(product);
            detail.setOrder(order);
            return detail;
        }).collect(Collectors.toList());

        order.setOrderDetails(orderDetails);
 
        // Save order
        Order savedOrder = orderRepository.save(order);
        return OrderMapper.INSTANCE.toResponse(savedOrder);
    }



    @Transactional
    public OrderResponse updateOrderPayment(Integer orderId, String paymentMethod, boolean paymentStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Update payment details
        order.setPaymentMethod(paymentMethod);
        order.setPaymentStatus(paymentStatus);
        order.setStatus("COMPLETED"); // Update order status as needed

        Order updatedOrder = orderRepository.save(order);
        return OrderMapper.INSTANCE.toResponse(updatedOrder);
    }


    public OrderResponse getOrderById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(OrderMapper.INSTANCE::toResponse)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

//    @Transactional
//    public OrderResponse updateOrder(Integer orderId, OrderRequest orderRequest) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new RuntimeException("Order not found"));
//
//        order.setOrderDate(orderRequest.getOrderDate());
//        order.setStatus(orderRequest.getStatus());
//        order.setPaymentStatus(orderRequest.isPaymentStatus());
//        order.setPaymentMethod(orderRequest.getPaymentMethod());
//
//        if (orderRequest.getCustomerId() > 0) {
//            Customer customer = customerRepository.findById(orderRequest.getCustomerId())
//                    .orElseThrow(() -> new RuntimeException("Customer not found"));
//            order.setCustomer(customer);
//        }
//
//        List<OrderDetail> orderDetails = orderRequest.getOrderDetails().stream().map(request -> {
//            OrderDetail detail = new OrderDetail();
//            detail.setQuantity(request.getQuantity());
//            detail.setPrice(request.getPrice());
//            Product product = productRepository.findByBarcode(request.getProductBarcode())
//                    .orElseThrow(() -> new RuntimeException("Product not found"));
//            detail.setProduct(product);
//            detail.setOrder(order);
//            return detail;
//        }).collect(Collectors.toList());
//
//        order.setOrderDetails(orderDetails);
//
//        Order updatedOrder = orderRepository.save(order);
//        return OrderMapper.INSTANCE.toResponse(updatedOrder);
//    }

    public boolean deleteOrder(Integer orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            return true;
        }
        return false;
    }

    public OrderReportResponse getOrderReports(LocalDate startDate, LocalDate endDate) {
        long totalOrders = 0;
        double totalRevenue = 0;
        double averageOrderValue = 0;
        Map<String, Integer> customerCountByWeek = new HashMap<>();
        Map<String, Integer> customerCountByMonth = new LinkedHashMap<>();
        Map<String, Integer> customerCountByYear = new LinkedHashMap<>();

        try {
            // Fetch total orders, revenue, and average order value
            totalOrders = getTotalOrders();
            totalRevenue = getTotalRevenue();
            averageOrderValue = getAverageOrderValue();

            // Fetch weekly customer count data
            List<Object[]> customersWeek = orderRepository.countCustomerOrderByWeek(startDate, endDate);
            for (Object[] result : customersWeek) {
                int year = (int) result[0];
                int week = (int) result[1];
                int count = ((Number) result[2]).intValue(); // Use intValue() instead of longValue()
                customerCountByWeek.put("Year " + year + " Week " + week, count);
            }

            // Fetch monthly customer count data
            List<Object[]> customersMonth = orderRepository.countCustomerOrderByMonth(startDate, endDate);
            for (Object[] result : customersMonth) {
                int year = (Integer) result[0];
                int month = (Integer) result[1];
                int count = ((Number) result[2]).intValue(); // Use intValue() instead of longValue()
                customerCountByMonth.put("Year " + year + " Month " + month, count);
            }

            // Fetch yearly customer count data
            List<Object[]> customersYear = orderRepository.countCustomerOrderByYear(startDate, endDate);
            for (Object[] result : customersYear) {
                int year = (Integer) result[0];
                int count = ((Number) result[1]).intValue(); // Use intValue() instead of longValue()
                customerCountByYear.put("Year " + year, count);
            }

        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // Handle the exception as needed
            throw new RuntimeException("An error occurred while generating the report", e);
        }

        return new OrderReportResponse(totalOrders, totalRevenue, averageOrderValue, customerCountByWeek, customerCountByMonth, customerCountByYear);
    }



    public long getTotalOrders() {
        return orderRepository.count();
    }

    public double getTotalRevenue() {
        return orderDetailRepository.findAll().stream()
                .mapToDouble(orderDetail -> orderDetail.getPrice() * orderDetail.getQuantity())
                .sum();
    }

    public double getAverageOrderValue() {
        long totalOrders = orderRepository.count();
        if (totalOrders == 0) {
            return 0;
        }
        double totalRevenue = getTotalRevenue();
        return totalRevenue / totalOrders;
    }


}
