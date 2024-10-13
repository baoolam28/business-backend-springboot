package com.onestep.business_management.Controller.BuyerController;

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
@RequestMapping("/api/buyer/shipping-addresses") // Đường dẫn chung cho tất cả các endpoint
public class ShippingAddressController {

    @Autowired
    private ShipmentAddressService shipmentAddressService;

    // Tạo hoặc cập nhật địa chỉ giao hàng
    @PostMapping
    public ResponseEntity<ShipmentAddressRespone> createOrUpdateShippingAddress(
            @RequestBody ShipmentAddressRequest shippingAddressRequest) {
        ShipmentAddressRespone response = shipmentAddressService.saveShippingAddress(shippingAddressRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Lấy địa chỉ giao hàng theo ID người dùng
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ShipmentAddressRespone>> getShippingAddressesByUserId(
            @PathVariable UUID userId) {
        List<ShipmentAddressRespone> addresses = shipmentAddressService.getShippingAddressById(userId);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    // Lấy tất cả địa chỉ giao hàng
    @GetMapping
    public ResponseEntity<List<ShipmentAddressRespone>> getAllShippingAddresses() {
        List<ShipmentAddressRespone> addresses = shipmentAddressService.getAllShippingAddresses();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    // Xóa địa chỉ giao hàng theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShippingAddress(@PathVariable Integer id) {
        shipmentAddressService.deleteShippingAddressById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
