package com.onestep.business_management.Service.ShipmentAddressService;

import com.onestep.business_management.DTO.ShipmentAddressDTO.ShipmentAddressRequest;
import com.onestep.business_management.DTO.ShipmentAddressDTO.ShipmentAddressRespone;
import com.onestep.business_management.Entity.ShippingAddress;
import com.onestep.business_management.Entity.User;
import com.onestep.business_management.Repository.ShippingAddressRepository;
import com.onestep.business_management.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShipmentAddressService {
    @Autowired
    private ShippingAddressRepository shipmentAddressRepository;

    @Autowired
    private UserRepository userRepository;

    // Tạo địa chỉ giao hàng
    public ShipmentAddressRespone createShippingAddress(ShipmentAddressRequest requestDTO) {
        UUID userId = requestDTO.getUserId().getUserId(); // Lấy userId từ đối tượng User
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + userId + " not found."));

        // Kiểm tra xem người dùng có địa chỉ mặc định nào chưa
        if (!requestDTO.isDisabled()) { // Nếu địa chỉ không bị vô hiệu hóa, tức là nó là địa chỉ mặc định
            List<ShippingAddress> existingAddresses = shipmentAddressRepository.findByUserId(userId);
            existingAddresses.stream()
                    .filter(address -> !address.isDisabled())
                    .findFirst()
                    .ifPresent(existing -> {
                        throw new ResponseStatusException(HttpStatus.CONFLICT,
                                "User already has a default shipping address.");
                    });
        }

        // Ánh xạ từ DTO sang entity
        ShippingAddress shippingAddress = ShipmentAddressMapper.INSTANCE.toEntity(requestDTO);
        shippingAddress.setUserId(user); // Set User entity cho ShippingAddress

        // Lưu entity vào database
        ShippingAddress savedAddress = shipmentAddressRepository.save(shippingAddress);

        // Ánh xạ từ entity sang DTO response
        return ShipmentAddressMapper.INSTANCE.toResponse(savedAddress);
    }

    // Cập nhật địa chỉ giao hàng
    public ShipmentAddressRespone updateShippingAddress(UUID id, ShipmentAddressRequest requestDTO) {
        ShippingAddress existingAddress = shipmentAddressRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Shipping address with id " + id + " not found."));

        // Kiểm tra nếu địa chỉ muốn cập nhật là địa chỉ mặc định khác
        if (!requestDTO.isDisabled() && !existingAddress.isDisabled()) {
            List<ShippingAddress> existingAddresses = shipmentAddressRepository
                    .findByUserId(existingAddress.getUserId().getUserId());
            existingAddresses.stream()
                    .filter(address -> !address.isDisabled() && !address.getAddressId().equals(id))
                    .findFirst()
                    .ifPresent(existing -> {
                        throw new ResponseStatusException(HttpStatus.CONFLICT,
                                "User already has another default shipping address.");
                    });
        }

        // Ánh xạ các thay đổi từ DTO sang entity
        ShipmentAddressMapper.INSTANCE.toEntity(requestDTO);

        // Lưu các thay đổi vào cơ sở dữ liệu
        ShippingAddress updatedAddress = shipmentAddressRepository.save(existingAddress);

        // Trả về DTO response
        return ShipmentAddressMapper.INSTANCE.toResponse(updatedAddress);
    }

    // Lấy địa chỉ giao hàng theo ID
    public ShipmentAddressRespone getShippingAddressById(UUID id) {
        ShippingAddress shippingAddress = shipmentAddressRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Shipping address with id " + id + " not found."));
        return ShipmentAddressMapper.INSTANCE.toResponse(shippingAddress);
    }

    // Lấy tất cả địa chỉ giao hàng cho một người dùng
    public List<ShipmentAddressRespone> getAllShippingAddressesByUserId(UUID userId) {
        List<ShippingAddress> addresses = shipmentAddressRepository.findByUserId(userId);
        return addresses.stream()
                .map(ShipmentAddressMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    // Xóa địa chỉ giao hàng theo ID
    public void deleteShippingAddressById(UUID id) {
        ShippingAddress shippingAddress = shipmentAddressRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Shipping address with id " + id + " not found."));

        // Kiểm tra nếu địa chỉ không bị vô hiệu hóa (là địa chỉ mặc định)
        if (!shippingAddress.isDisabled()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot delete the default shipping address.");
        }

        shipmentAddressRepository.deleteById(id);
    }
}
