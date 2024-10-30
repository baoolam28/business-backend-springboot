package com.onestep.business_management.Service.ShipmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onestep.business_management.DTO.ShippingDTO.ShipmentResponse;
import com.onestep.business_management.Entity.Shipment;
import com.onestep.business_management.Exeption.ResourceNotFoundException;
import com.onestep.business_management.Repository.ShipmentRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    public ShipmentResponse getShipmentById(Integer shipmentId) {

        Shipment shipment = shipmentRepository.findById(shipmentId).orElseThrow(
            () -> new ResourceNotFoundException("Shipment not found")
        );

        return ShipmentMapper.INSTANCE.toResponse(shipment);
    }

    public List<ShipmentResponse> getOrdersOnlineByUser(UUID userId) {
        List<Shipment> shipments = shipmentRepository.findShipmentsByUserId(userId);

        if (shipments.isEmpty()) {
            throw new ResourceNotFoundException("There are no orders yet for user ID: " + userId);
        }

        return shipments.stream()
                .map(ShipmentMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }
}
