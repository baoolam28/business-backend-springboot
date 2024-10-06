package com.onestep.business_management.Service.OrderOnlineService;

import com.onestep.business_management.DTO.OrderDTO.OrderOnlineDetailRequest;
import com.onestep.business_management.DTO.OrderDTO.OrderOnlineRequest;
import com.onestep.business_management.DTO.OrderDTO.OrderOnlineResponse;
import com.onestep.business_management.Entity.OrderOnline;
import com.onestep.business_management.Entity.OrderOnlineDetail;
import com.onestep.business_management.Exeption.ResourceNotFoundException;
import com.onestep.business_management.Repository.OrderOnlineRepository;

import com.onestep.business_management.Utils.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderOnlineService {
    @Autowired
    private OrderOnlineRepository orderOnlineRepository;

    @Autowired
    private MapperService mapperService;

    public OrderOnlineResponse create_order_online(OrderOnlineRequest request){
        return null;
    }

    public List<OrderOnlineResponse> createMultipleOrders(OrderOnlineRequest orderRequest) {
        List<OrderOnlineResponse> responses = new ArrayList<>();

        // Nhóm các sản phẩm theo storeId
        Map<UUID, List<OrderOnlineDetailRequest>> storeGroupedDetails = orderRequest.getOrderOnlineDetailRequests().stream()
                .collect(Collectors.groupingBy(OrderOnlineDetailRequest::getStoreId));

        // Tạo đơn hàng cho mỗi cửa hàng
        for (Map.Entry<UUID, List<OrderOnlineDetailRequest>> entry : storeGroupedDetails.entrySet()) {
            UUID storeId = entry.getKey(); // Kiểu UUID
            List<OrderOnlineDetailRequest> details = entry.getValue();

            OrderOnline order = new OrderOnline();
            order.setOrderDate(new Date());
            order.setStatus(OrderOnline.Status.CHUA_GIAO_HANG); // Đơn hàng mới
            order.setPaymentStatus(false); // Trạng thái thanh toán ban đầu
            order.setPaymentMethod(orderRequest.getPaymentMethod());
            order.setUser(mapperService.findUserById(orderRequest.getUserId())); // Gán người dùng
            order.setStore(mapperService.findStoreById(storeId)); // Gán cửa hàng (UUID)

            // Xử lý các chi tiết của đơn hàng
            List<OrderOnlineDetail> orderDetails = details.stream().map(detailRequest -> {
                OrderOnlineDetail detail = new OrderOnlineDetail();
                detail.setQuantity(detailRequest.getQuantity());
                detail.setPrice(detailRequest.getPrice());
                detail.setBarcode(detailRequest.getBarcode());
                detail.setProduct(mapperService.findProductByBarcode(detailRequest.getBarcode()));
                detail.setOrderOnline(order); // Gán chi tiết cho đơn hàng
                return detail;
            }).collect(Collectors.toList());

            order.setOrderDetails(orderDetails);

            // Lưu đơn hàng vào cơ sở dữ liệu
            OrderOnline savedOrder = orderOnlineRepository.save(order);

            // Chuyển đổi sang response
            OrderOnlineResponse response = OrderOnlineMapper.INSTANCE.toResponse(savedOrder);
            responses.add(response);
        }

        return responses;
    }


    public List<OrderOnlineResponse> getOrdersOnlineByUser(UUID userId) {

        List<OrderOnline> orders = orderOnlineRepository.findByUser(userId);

        if(orders.isEmpty()){
            throw new ResourceNotFoundException("There are no orders yet!");
        }

        return orders.stream()
                .map(OrderOnlineMapper.INSTANCE::toResponse)
                .toList();
    }


}
