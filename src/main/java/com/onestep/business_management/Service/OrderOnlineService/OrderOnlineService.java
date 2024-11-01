package com.onestep.business_management.Service.OrderOnlineService;

import com.onestep.business_management.DTO.OrderDTO.OrderOnlineDetailRequest;
import com.onestep.business_management.DTO.OrderDTO.OrderOnlineRequest;
import com.onestep.business_management.DTO.OrderDTO.OrderOnlineResponse;
import com.onestep.business_management.Entity.*;
import com.onestep.business_management.Exeption.ResourceNotFoundException;
import com.onestep.business_management.Repository.OrderOnlineRepository;
import com.onestep.business_management.Repository.ShipmentRepository;
import com.onestep.business_management.Utils.MapperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderOnlineService {

    private static final Logger logger = LoggerFactory.getLogger(OrderOnlineService.class);

    @Autowired
    private OrderOnlineRepository orderOnlineRepository;

    @Autowired
    private MapperService mapperService;

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Transactional
    public List<OrderOnlineResponse> createMultipleOrders(OrderOnlineRequest orderRequest) {
        List<OrderOnlineResponse> responses = new ArrayList<>();

        // Group the products by storeId
        Map<UUID, List<OrderOnlineDetailRequest>> storeGroupedDetails = orderRequest.getOrderOnlineDetailRequests()
                .stream()
                .collect(Collectors.groupingBy(OrderOnlineDetailRequest::getStoreId));

        // Create orders and shipments for each store
        for (Map.Entry<UUID, List<OrderOnlineDetailRequest>> entry : storeGroupedDetails.entrySet()) {
            UUID storeId = entry.getKey();
            List<OrderOnlineDetailRequest> details = entry.getValue();

            // Create an order for the store
            OrderOnline order = new OrderOnline();
            order.setOrderDate(new Date());
            order.setStatus(OrderOnline.Status.CHO_XAC_NHAN);
            order.setPaymentStatus(false); // Initial payment status
            order.setPaymentMethod(orderRequest.getPaymentMethod());
            order.setUser(mapperService.findUserById(orderRequest.getUserId()));
            order.setStore(mapperService.findStoreById(storeId));

            // Process each order detail and add them to the order
            List<OrderOnlineDetail> orderDetails = new ArrayList<>();
            for (OrderOnlineDetailRequest detailRequest : details) {
                OrderOnlineDetail detail = new OrderOnlineDetail();
                detail.setQuantity(detailRequest.getQuantity());
                ProductDetail productDetail = mapperService.findProductDetailById(detailRequest.getProductDetailId());
                detail.setPrice(productDetail.getPrice());
                detail.setProductDetail(productDetail);
                detail.setOrderOnline(order); // Set the order reference
                orderDetails.add(detail); // Add detail to list
            }

            order.setOrderDetails(orderDetails);

            // Save the OrderOnline first
            OrderOnline savedOrder = orderOnlineRepository.save(order); // Save the order first

            // Create and save shipments
            ShippingAddress address = mapperService.findShippingAddressById(orderRequest.getAddressId());
            List<Shipment> shipments = createShipments(orderRequest, savedOrder, address); // Create multiple shipments

            // Set the shipments to the saved order
            savedOrder.setShipments(shipments); // Assuming you have a setShipments method

            // Save the order again to persist the shipments
            orderOnlineRepository.save(savedOrder); // Ensure the order with shipments is saved

            // Convert the saved order to response format
            OrderOnlineResponse response = OrderOnlineMapper.INSTANCE.toResponse(savedOrder);
            responses.add(response);
        }

        return responses;
    }

    private List<Shipment> createShipments(OrderOnlineRequest orderRequest, OrderOnline savedOrder, ShippingAddress address) {
        List<Shipment> shipments = new ArrayList<>();

        // Create a shipment (or multiple if needed)
        Shipment shipment = new Shipment();
        shipment.setCreateAt(new Date());
        shipment.setShippingAddress(address);
        shipment.setShippingFee(orderRequest.getShippingFee());
        shipment.setShippingMethod(orderRequest.getShippingMethod());
        shipment.setExpectedDeliverDate(orderRequest.getExpectedDeliverDate());
        shipment.setShippingStatus(Shipment.ShippingStatus.CHO_XAC_NHAN);
        shipment.setOrderOnline(savedOrder); // Set the reference to the saved order

        shipments.add(shipmentRepository.save(shipment)); // Save the shipment to the database

        return shipments; // Return the list of shipments
    }

    public List<OrderOnlineResponse> getOrdersOnlineByUser(UUID userId) {
        List<OrderOnline> orders = orderOnlineRepository.findByUser(userId);

        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("There are no orders yet for user ID: " + userId);
        }

        return orders.stream()
                .map(OrderOnlineMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }
}
