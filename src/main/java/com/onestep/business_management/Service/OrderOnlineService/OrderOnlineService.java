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
        try {
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

                // Save the order to the database first
                OrderOnline savedOrder = orderOnlineRepository.save(order);
                logger.info("Saved OrderOnline: {}", savedOrder.getOrderOnlineId());

                // Process each order detail and add them to the order
                List<OrderOnlineDetail> orderDetails = details.stream()
                        .map(detailRequest -> {
                            OrderOnlineDetail detail = new OrderOnlineDetail();
                            detail.setQuantity(detailRequest.getQuantity());
                            ProductDetail productDetail = mapperService.findProductDetailById(detailRequest.getProductDetailId());
                            detail.setPrice(productDetail.getPrice());
                            detail.setProductDetail(productDetail);
                            detail.setOrderOnline(savedOrder); // Set the order reference
                            return detail;
                        })
                        .collect(Collectors.toList());

                // Set the order details to the order
                savedOrder.setOrderDetails(orderDetails);
                orderOnlineRepository.save(savedOrder); // Save the order again to persist order details

                // Create or update the shipment for the order associated with the store
                ShippingAddress address = mapperService.findShippingAddressById(orderRequest.getAddressId());
                Shipment shipment = createOrUpdateShipment(orderRequest, savedOrder, address);
                logger.info("Processed Shipment for OrderOnline ID: {}", savedOrder.getOrderOnlineId());

                // Convert the saved order to response format
                OrderOnlineResponse response = OrderOnlineMapper.INSTANCE.toResponse(savedOrder);
                System.out.println("response: "+response.toString());
                responses.add(response);
            }

            return responses;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private Shipment createOrUpdateShipment(OrderOnlineRequest orderRequest, OrderOnline savedOrder, ShippingAddress address) {
        Shipment shipment;
        Optional<Shipment> existingShipmentOpt = shipmentRepository.findByOrderOnline(savedOrder);

        if (existingShipmentOpt.isPresent()) {
            // If it exists, update the existing shipment with new details
            shipment = existingShipmentOpt.get();
            shipment.setShippingFee(orderRequest.getShippingFee());
            shipment.setShippingMethod(orderRequest.getShippingMethod());
            shipment.setExpectedDeliverDate(orderRequest.getExpectedDeliverDate());
            shipment.setShippingAddress(address); // Update address if needed
            logger.info("Updated Shipment for OrderOnline ID: {}", savedOrder.getOrderOnlineId());
        } else {
            // If it doesn't exist, create a new shipment
            shipment = new Shipment();
            shipment.setCreateAt(new Date());
            shipment.setShippingAddress(address);
            shipment.setShippingFee(orderRequest.getShippingFee());
            shipment.setShippingMethod(orderRequest.getShippingMethod());
            shipment.setExpectedDeliverDate(orderRequest.getExpectedDeliverDate());
            shipment.setShippingStatus(Shipment.ShippingStatus.CHO_XAC_NHAN);
            shipment.setOrderOnline(savedOrder); // Link shipment to the order
            logger.info("Created new Shipment for OrderOnline ID: {}", savedOrder.getOrderOnlineId());
        }

        return shipmentRepository.save(shipment); // Save the shipment (either new or updated)
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
