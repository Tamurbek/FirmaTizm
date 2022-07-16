package com.example.topshiriqfirma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse {
    private String message;
    private Boolean turi;
    private Object object;

    public ApiResponse(String message,Boolean turi){
        this.message=message;
        this.turi=turi;
    }
}
