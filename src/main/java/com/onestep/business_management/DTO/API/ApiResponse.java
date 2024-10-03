package com.onestep.business_management.DTO.API;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private int statusCode;
    private String message;
    private T data;
    private LocalDateTime date;

    public ApiResponse(int statusCode, String message) {
        this(statusCode, message, null, LocalDateTime.now());
    }
}
