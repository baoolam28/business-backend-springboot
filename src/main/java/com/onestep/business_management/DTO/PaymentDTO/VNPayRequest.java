package com.onestep.business_management.DTO.PaymentDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VNPayRequest {
    private UUID orderId;
}
