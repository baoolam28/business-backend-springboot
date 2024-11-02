package com.onestep.business_management.DTO.qrCodeDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor  
public class qrCodeRequest {
    
    private String accountNo;
    private String accountName;
    private String acqId;
    private String addInfo;
    private String amount;
    private String template;
}
