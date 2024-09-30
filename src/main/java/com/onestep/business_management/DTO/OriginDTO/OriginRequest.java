package com.onestep.business_management.DTO.OriginDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OriginRequest {
    private Integer originId;
    private String originName;
}
