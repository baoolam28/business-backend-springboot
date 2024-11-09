package com.onestep.business_management.DTO.DocumentDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentRequest {
    private UUID storeId;
    private String docNumberOne;
    private Date date;
    private String docNumberTwo;
    private String companyId;
    private String representOne;
    private String representTwo;
    private Float totalAmount;
    private Float paidAmount;
    private Float paymentPercentage;
    private boolean paymentStatus;
    private UUID createBy;
    private List<DocumentDetailRequest> documentDetails; // List of DocumentDetails
}
