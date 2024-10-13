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
    private ShippingAddressRepository shippingAddressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShipmentAddressMapper shippingAddressMapper;

    // Create or Update Shipping Address
    public ShipmentAddressRespone saveShippingAddress(ShipmentAddressRequest shippingAddressRequest) {
        UUID uuid = UUID.fromString(shippingAddressRequest.getUserId());
        User user = userRepository.findById(uuid).orElse(null);

        if (user == null) {
            throw new RuntimeException("User not found with id: " + shippingAddressRequest.getUserId());
        }

        // Lấy tất cả địa chỉ giao hàng của user
        List<ShippingAddress> existingAddresses = shippingAddressRepository.findByUser(user);
        String newAddress = shippingAddressRequest.getAddress();
        String newWardCode = shippingAddressRequest.getWardCode();
        String strCompare = newWardCode + newAddress;

        // Kiểm tra xem địa chỉ mới có trùng với địa chỉ đã có hay không
        boolean checkExistingAddress = existingAddresses.stream()
                .anyMatch(address -> (address.getWardCode() + address.getAddress()).equals(strCompare));

        if (checkExistingAddress) {
            // Địa chỉ đã tồn tại, không cho phép thêm
            throw new RuntimeException("Address with the same wardCode and address already exists.");
        }

        // Map request thành entity và lưu địa chỉ mới
        ShippingAddress shippingAddress = shippingAddressMapper.toEntity(shippingAddressRequest);
        shippingAddress.setUser(user);
        ShippingAddress savedShippingAddress = shippingAddressRepository.save(shippingAddress);
        return shippingAddressMapper.toResponse(savedShippingAddress);
    }

    // Get Shipping Address by ID
    public List<ShipmentAddressRespone> getShippingAddressById(UUID id) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            List<ShippingAddress> shippingAddresses = shippingAddressRepository.findByUser(existingUser);
            return shippingAddresses.stream()
                    .map(shippingAddressMapper::toResponse)
                    .collect(Collectors.toList());
        }
        return null;
    }

    // Get All Shipping Addresses
    public List<ShipmentAddressRespone> getAllShippingAddresses() {
        List<ShippingAddress> shippingAddresses = shippingAddressRepository.findAll();
        return shippingAddresses.stream()
                .map(shippingAddressMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Delete Shipping Address by ID
    public void deleteShippingAddressById(Integer id) {
        shippingAddressRepository.deleteById(id);
    }
}