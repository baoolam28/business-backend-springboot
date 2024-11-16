package com.onestep.business_management.DTO.ReportDTO;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequest {
    
    private UUID storeId;
    private Date startDate;
    private Date endDate;
}
