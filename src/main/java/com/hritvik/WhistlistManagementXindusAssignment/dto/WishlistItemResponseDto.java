package com.hritvik.WhistlistManagementXindusAssignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistItemResponseDto {

    private String name;
    private String description;
    private double price;
    private String userName;

    public WishlistItemResponseDto(String name, String description) {
    }
}
