package com.onestep.business_management.DTO.DocumentDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDetailRequest {
    private String barcode; // Barcode of the product
    private int quantity; // Quantity of the product
    private float price; // Price per unit of the product
    private float totalPrice; // Total price for the line item
}
