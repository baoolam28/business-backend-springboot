package com.onestep.business_management.DTO.ReportDTO;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {
    
    // private UUID orderOfflineId;
    private Double total;
    private Double totalByDay;
    private Double totalByMonth;
    private Double totalByYear;
}
