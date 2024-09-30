package com.onestep.business_management.DTO.DocumentDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDetailResponse {
    private UUID docDetailId;
    private String productBarcode; // Barcode of the product
    private int quantity; // Quantity of the product
    private float price; // Price per unit of the product
    private float totalPrice; // Total price for the line item
    private Date createdDate;
}
