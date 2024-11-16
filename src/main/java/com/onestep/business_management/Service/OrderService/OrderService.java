package com.onestep.business_management.Service.OrderService;

import com.onestep.business_management.Entity.*;
import com.onestep.business_management.DTO.OrderDTO.OrderOnlineDetailResponse;
import com.itextpdf.text.List;
import com.onestep.business_management.DTO.OrderDTO.OrderDetailRequest;
import com.onestep.business_management.DTO.OrderDTO.OrderDetailResponse;

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
         try {
             OrderOffline order = OrderMapper.INSTANCE.toEntity(orderRequest, mapperService);
             order.setOrderDate(new Date());
             order.setStatus("PENDING"); // Order is pending payment
             order.setPaymentStatus(false); // Payment status is initially unpaid
             order.setPaymentMethod(null); // No payment method initially

            java.util.List<OrderOfflineDetail> OrderOfflineDetails = orderRequest.getOrderDetails().stream().map(request -> {
                 OrderOfflineDetail detail = new OrderOfflineDetail();
                 detail.setQuantity(request.getQuantity());
                 detail.setPrice(request.getPrice());
                 detail.setBarcode(request.getBarcode());
                 Product product = mapperService.findProductInStore(request.getStoreId(), request.getBarcode());
                 detail.setProduct(product);
                 detail.setOrderOffline(order);
                 // Logic to fetch product by barcode
                 return detail;
             }).toList();

             order.setOrderDetails(OrderOfflineDetails);



             // Save order
             OrderOffline savedOrder = orderRepository.save(order);
             return OrderMapper.INSTANCE.toResponse(savedOrder);
         }catch (Exception e){
             System.out.println(e.getMessage());
         }
         return null;
     }


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

    public java.util.List<OrderResponse> getAllOrders() {
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


// public OrderReportResponse getOrderReports(LocalDate startDate, LocalDate endDate) {
//         long totalOrders = 0;
//         double totalRevenue = 0;
//         double averageOrderValue = 0;

//         Map<String, Integer> customerCountByWeek = new HashMap<>();
//         Map<String, Integer> customerCountByMonth = new LinkedHashMap<>();
//         Map<String, Integer> customerCountByYear = new LinkedHashMap<>();

//         try {
//             // Fetch total orders, revenue, and average order value
//             totalOrders = getTotalOrders(); // Đảm bảo phương thức này tồn tại và hoạt động đúng
//             totalRevenue = getTotalRevenue(); // Đảm bảo phương thức này tồn tại và hoạt động đúng
//             averageOrderValue = getAverageOrderValue(); // Đảm bảo phương thức này tồn tại và hoạt động đúng

//             // Fetch weekly customer count data
//             java.util.List<Object[]> customersWeek = orderRepository.countCustomerOrderByWeek(startDate, endDate);
//             for (Object[] result : customersWeek) {
//                 int year = (int) result[0];
//                 int week = (int) result[1];
//                 int count = ((Number) result[2]).intValue(); // Sử dụng intValue() thay vì longValue()
//                 customerCountByWeek.put("Year " + year + " Week " + week, count);
//             }

//             // Fetch monthly customer count data
//             java.util.List<Object[]> customersMonth = orderRepository.countCustomerOrderByMonth(startDate, endDate);
//             for (Object[] result : customersMonth) {
//                 int year = (Integer) result[0];
//                 int month = (Integer) result[1];
//                 int count = ((Number) result[2]).intValue(); // Sử dụng intValue() thay vì longValue()
//                 customerCountByMonth.put("Year " + year + " Month " + month, count);
//             }

//             // Fetch yearly customer count data
//             java.util.List<Object[]> customersYear = orderRepository.countCustomerOrderByYear(startDate, endDate);
//             for (Object[] result : customersYear) {
//                 int year = (Integer) result[0];
//                 int count = ((Number) result[1]).intValue(); // Sử dụng intValue() thay vì longValue()
//                 customerCountByYear.put("Year " + year, count);
//             }

//         } catch (Exception e) {
//             e.printStackTrace(); // In ra lỗi để dễ dàng gỡ lỗi
//             throw new RuntimeException("An error occurred while generating the report", e);
//         }

//         return new OrderReportResponse(totalOrders, totalRevenue, averageOrderValue, customerCountByWeek,
//                 customerCountByMonth, customerCountByYear);
//     }

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


    public java.util.List<OrderResponse> getAllOrdersByStoreId(UUID storeId) {
        java.util.List<OrderOffline> orders = orderRepository.findBystore(storeId);


        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("No orders found for Store ID: " + storeId);
        }

        return orders.stream()
                .map(OrderMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

     public java.util.List<OrderDetailResponse> getProductsByOrderId(UUID orderId) {
        // Tìm kiếm OrderOffline dựa trên orderId
        OrderOffline orderOffline = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Sử dụng OrderMapper để chuyển đổi orderDetails thành danh sách OrderDetailResponse
        return OrderMapper.INSTANCE.mapOrderDetailsToResponses(orderOffline.getOrderDetails());
    }

    public OrderResponse updateOrderDetail(OrderRequest request, UUID orderId) {
        // Retrieve the OrderOffline entity by ID
        OrderOffline orderOffline = orderRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException("Order offline not found"));

        java.util.List<OrderDetailRequest> orderDetailRequests = request.getOrderDetails();
        java.util.List<OrderOfflineDetail> updatedOrderDetails = orderDetailRequests.stream()
                .map(detailRequest -> {
                    OrderOfflineDetail orderOfflineDetail = new OrderOfflineDetail();
                    Product product = productRepository.findById(detailRequest.getProductId()).orElseThrow(
                        () -> new ResourceNotFoundException("Product ID not found")
                    );
                    orderOfflineDetail.setProduct(product);
                    orderOfflineDetail.setBarcode(product.getBarcode());
                    orderOfflineDetail.setPrice(product.getPrice());
                    orderOfflineDetail.setQuantity(detailRequest.getQuantity());
                    orderOfflineDetail.setOrderOffline(orderOffline);
                    // Set other fields as needed
                    return orderOfflineDetail;
                })
                .collect(Collectors.toList());

                Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(
                    () -> new ResourceNotFoundException("Customer ID not found")
                );
                orderOffline.setCustomer(customer);

        // Clear the existing order details and set the updated list
        orderOffline.getOrderDetails().clear();
        orderOffline.getOrderDetails().addAll(updatedOrderDetails);

        // Save the updated orderOffline entity
        OrderOffline response = orderRepository.save(orderOffline);

        return OrderMapper.INSTANCE.toResponse(response);
    }
}