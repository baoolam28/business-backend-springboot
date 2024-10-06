package com.onestep.business_management.Service.OrderService;

import com.onestep.business_management.Entity.*;
import com.onestep.business_management.DTO.OrderDTO.OrderReportResponse;
import com.onestep.business_management.DTO.OrderDTO.OrderRequest;
import com.onestep.business_management.DTO.OrderDTO.OrderResponse;
import com.onestep.business_management.Exeption.ResourceNotFoundException;
import com.onestep.business_management.Repository.*;
import com.onestep.business_management.Utils.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderOfflineRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderOfflineDetailRepository orderDetailRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private MapperService mapperService;

    public OrderResponse createOrder(OrderRequest orderRequest) {
        if (orderRequest.getStoreId() == null) {
            throw new RuntimeException("Store ID cannot be null");
        }

        try {
            // Tiến hành tạo đơn hàng
            OrderOffline order = OrderMapper.INSTANCE.toEntity(orderRequest, mapperService);
            order.setOrderDate(new Date());
            order.setStatus("PENDING");
            order.setPaymentStatus(false);
            order.setPaymentMethod(null);

            List<OrderOfflineDetail> orderDetails = orderRequest.getOrderDetails().stream().map(request -> {
                OrderOfflineDetail detail = new OrderOfflineDetail();
                detail.setQuantity(request.getQuantity());
                detail.setPrice(request.getPrice());
                detail.setBarcode(request.getBarcode());
                // Kiểm tra sản phẩm trong cửa hàng
                Product product = mapperService.findProductInStore(orderRequest.getStoreId(), request.getBarcode());
                if (product == null) {
                    throw new RuntimeException("Not found product with barcode = " + request.getBarcode()
                            + " in store: " + orderRequest.getStoreId());
                }
                detail.setProduct(product);
                detail.setOrderOffline(order);
                return detail;
            }).toList();

            order.setOrderDetails(orderDetails);

            // Lưu đơn hàng
            OrderOffline savedOrder = orderRepository.save(order);
            return OrderMapper.INSTANCE.toResponse(savedOrder);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error creating order: " + e.getMessage());
        }
    }

    @Transactional
    public OrderResponse updateOrderPayment(UUID orderId, String paymentMethod, boolean paymentStatus) {
        // Tìm kiếm đơn hàng theo ID
        OrderOffline order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        // Cập nhật thông tin thanh toán
        order.setPaymentMethod(paymentMethod);
        order.setPaymentStatus(paymentStatus);

        // Cập nhật trạng thái đơn hàng nếu cần
        if (paymentStatus) {
            order.setStatus("COMPLETED"); // Cập nhật trạng thái thành "COMPLETED" nếu đã thanh toán
        }

        // Lưu lại đơn hàng đã cập nhật
        OrderOffline updatedOrder = orderRepository.save(order);
        return OrderMapper.INSTANCE.toResponse(updatedOrder); // Trả về phản hồi
    }

    public OrderResponse getOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .map(OrderMapper.INSTANCE::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found")); // Sử dụng ngoại lệ cụ thể hơn
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    // @Transactional
    // public OrderResponse updateOrder(Integer orderId, OrderRequest orderRequest)
    // {
    // Order order = orderRepository.findById(orderId)
    // .orElseThrow(() -> new RuntimeException("Order not found"));
    //
    // order.setOrderDate(orderRequest.getOrderDate());
    // order.setStatus(orderRequest.getStatus());
    // order.setPaymentStatus(orderRequest.isPaymentStatus());
    // order.setPaymentMethod(orderRequest.getPaymentMethod());
    //
    // if (orderRequest.getCustomerId() > 0) {
    // Customer customer = customerRepository.findById(orderRequest.getCustomerId())
    // .orElseThrow(() -> new RuntimeException("Customer not found"));
    // order.setCustomer(customer);
    // }
    //
    // List<OrderDetail> orderDetails =
    // orderRequest.getOrderDetails().stream().map(request -> {
    // OrderDetail detail = new OrderDetail();
    // detail.setQuantity(request.getQuantity());
    // detail.setPrice(request.getPrice());
    // Product product =
    // productRepository.findByBarcode(request.getProductBarcode())
    // .orElseThrow(() -> new RuntimeException("Product not found"));
    // detail.setProduct(product);
    // detail.setOrder(order);
    // return detail;
    // }).collect(Collectors.toList());
    //
    // order.setOrderDetails(orderDetails);
    //
    // Order updatedOrder = orderRepository.save(order);
    // return OrderMapper.INSTANCE.toResponse(updatedOrder);
    // }

    public boolean deleteOrder(UUID orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            return true;
        }
        return false;
    }

    public OrderReportResponse getOrderReports(LocalDate startDate, LocalDate endDate) {
        long totalOrders;
        double totalRevenue;
        double averageOrderValue;
        Map<String, Integer> customerCountByWeek = new HashMap<>();
        Map<String, Integer> customerCountByMonth = new LinkedHashMap<>();
        Map<String, Integer> customerCountByYear = new LinkedHashMap<>();

        try {
            // Fetch total orders, revenue, and average order value
            totalOrders = getTotalOrders(); // Đảm bảo phương thức này tồn tại và hoạt động đúng
            totalRevenue = getTotalRevenue(); // Đảm bảo phương thức này tồn tại và hoạt động đúng
            averageOrderValue = getAverageOrderValue(); // Đảm bảo phương thức này tồn tại và hoạt động đúng

            // Fetch weekly customer count data
            List<Object[]> customersWeek = orderRepository.countCustomerOrderByWeek(startDate, endDate);
            for (Object[] result : customersWeek) {
                int year = (int) result[0];
                int week = (int) result[1];
                int count = ((Number) result[2]).intValue(); // Sử dụng intValue() thay vì longValue()
                customerCountByWeek.put("Year " + year + " Week " + week, count);
            }

            // Fetch monthly customer count data
            List<Object[]> customersMonth = orderRepository.countCustomerOrderByMonth(startDate, endDate);
            for (Object[] result : customersMonth) {
                int year = (Integer) result[0];
                int month = (Integer) result[1];
                int count = ((Number) result[2]).intValue(); // Sử dụng intValue() thay vì longValue()
                customerCountByMonth.put("Year " + year + " Month " + month, count);
            }

            // Fetch yearly customer count data
            List<Object[]> customersYear = orderRepository.countCustomerOrderByYear(startDate, endDate);
            for (Object[] result : customersYear) {
                int year = (Integer) result[0];
                int count = ((Number) result[1]).intValue(); // Sử dụng intValue() thay vì longValue()
                customerCountByYear.put("Year " + year, count);
            }

        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi để dễ dàng gỡ lỗi
            throw new RuntimeException("An error occurred while generating the report", e);
        }

        return new OrderReportResponse(totalOrders, totalRevenue, averageOrderValue, customerCountByWeek,
                customerCountByMonth, customerCountByYear);
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
