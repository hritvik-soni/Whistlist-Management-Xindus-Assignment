package com.hritvik.WhistlistManagementXindusAssignment.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto {

    private String username;

    private String password;

    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    public UserRequestDto(String newUser, String password, String email, String name) {
    }
}
