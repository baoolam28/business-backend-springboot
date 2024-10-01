package com.onestep.business_management.DTO.DocumentDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentRequest {
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
    private List<DocumentDetailRequest> documentDetails; // List of DocumentDetails
}
