package com.onestep.business_management.Controller;

import com.onestep.business_management.DTO.ShipmentAddressDTO.ShipmentAddressRequest;
import com.onestep.business_management.DTO.ShipmentAddressDTO.ShipmentAddressRespone;
import com.onestep.business_management.Service.ShipmentAddressService.ShipmentAddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/shippingAddress")
public class ShippingAddressController {

    @Autowired
    private ShipmentAddressService shipmentAddressService;

    // Tạo địa chỉ giao hàng mới
    @PostMapping
    public ResponseEntity<ShipmentAddressRespone> createShippingAddress(@RequestBody ShipmentAddressRequest request) {
        ShipmentAddressRespone response = shipmentAddressService.createShippingAddress(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Cập nhật địa chỉ giao hàng theo ID
    @PutMapping("/{id}")
    public ResponseEntity<ShipmentAddressRespone> updateShippingAddress(
            @PathVariable UUID id,
            @RequestBody ShipmentAddressRequest request) {
        ShipmentAddressRespone response = shipmentAddressService.updateShippingAddress(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Lấy địa chỉ giao hàng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ShipmentAddressRespone> getShippingAddressById(@PathVariable UUID id) {
        ShipmentAddressRespone response = shipmentAddressService.getShippingAddressById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Lấy danh sách địa chỉ giao hàng theo UserId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ShipmentAddressRespone>> getAllShippingAddressesByUserId(@PathVariable UUID userId) {
        List<ShipmentAddressRespone> responseList = shipmentAddressService.getAllShippingAddressesByUserId(userId);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    // Xóa địa chỉ giao hàng theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShippingAddressById(@PathVariable UUID id) {
        shipmentAddressService.deleteShippingAddressById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
