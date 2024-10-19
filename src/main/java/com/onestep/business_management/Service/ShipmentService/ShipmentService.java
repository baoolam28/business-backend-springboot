package com.onestep.business_management.Service.ShipmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onestep.business_management.DTO.OrderDTO.OrderOnlineResponse;
import com.onestep.business_management.DTO.ShippingDTO.ShipmentResponse;
import com.onestep.business_management.Entity.OrderOnline;
import com.onestep.business_management.Entity.Shipment;
import com.onestep.business_management.Exeption.ResourceNotFoundException;
import com.onestep.business_management.Repository.OrderOnlineRepository;
import com.onestep.business_management.Repository.ShipmentRepository;
import com.onestep.business_management.Service.OrderOnlineService.OrderOnlineMapper;

import java.util.*;

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
}
