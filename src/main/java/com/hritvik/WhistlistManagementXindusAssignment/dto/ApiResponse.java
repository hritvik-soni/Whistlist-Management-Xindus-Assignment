package com.hritvik.WhistlistManagementXindusAssignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {

   private boolean success;
    private Object Result;

    public ApiResponse(ApiResponse apiResponse) {
    }
}
