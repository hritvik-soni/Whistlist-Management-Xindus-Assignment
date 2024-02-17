package com.hritvik.WhistlistManagementXindusAssignment.controller;


import com.hritvik.WhistlistManagementXindusAssignment.dto.ApiResponse;
import com.hritvik.WhistlistManagementXindusAssignment.dto.UserUpdateRequestDto;
import com.hritvik.WhistlistManagementXindusAssignment.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testUpdateUser() {
        // Mock user details
        UserDetails userDetails = new User("testuser", "password", new ArrayList<>());

        // Mock user update request DTO
        UserUpdateRequestDto userUpdateDto = new UserUpdateRequestDto();
        userUpdateDto.setAddress("New Address");

        // Mock the userService.updateUser() method
        Mockito.when(userService.updateUser(userDetails.getUsername(), userUpdateDto))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        // Call the updateUser() method in the UserController
        ResponseEntity<?> response = userController.updateUser(userDetails, userUpdateDto);

        // Verify that the userService.updateUser() method was called with the correct parameters
        Mockito.verify(userService).updateUser(userDetails.getUsername(), userUpdateDto);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testDeleteUser() {
        // Mock user details
        UserDetails userDetails = new User("testuser", "password", new ArrayList<>());

        // Mock the userService.deleteUser() method
        Mockito.when(userService.deleteUser(userDetails.getUsername()))
                .thenReturn(new ResponseEntity<>( HttpStatus.OK));

        // Call the deleteUser() method in the UserController
        ResponseEntity<?> response = userController.deleteUser(userDetails);

        // Verify that the userService.deleteUser() method was called with the correct parameter
        Mockito.verify(userService).deleteUser(userDetails.getUsername());

        // Verify the response
      assertEquals(HttpStatus.OK, response.getStatusCode());

    }
    @Test
    public void testGetUsers() {
        // Mock user details
        UserDetails userDetails = new User("testuser", "password", new ArrayList<>());

        // Mock the userService.getUsers() method
        Mockito.when(userService.getUsers(userDetails.getUsername()))
                .thenReturn(new ResponseEntity<>( HttpStatus.OK));

        // Call the getUsers() method in the UserController
        ResponseEntity<?> response = userController.getUsers(userDetails);

        // Verify that the userService.getUsers() method was called with the correct parameter
        Mockito.verify(userService).getUsers(userDetails.getUsername());

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }
}