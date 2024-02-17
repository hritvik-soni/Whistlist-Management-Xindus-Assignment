package com.hritvik.WhistlistManagementXindusAssignment.controller;

import com.hritvik.WhistlistManagementXindusAssignment.config.JwtUtil;
import com.hritvik.WhistlistManagementXindusAssignment.dto.ApiResponse;
import com.hritvik.WhistlistManagementXindusAssignment.dto.AuthenticationRequestDto;
import com.hritvik.WhistlistManagementXindusAssignment.dto.AuthenticationResponse;
import com.hritvik.WhistlistManagementXindusAssignment.dto.UserRequestDto;
import com.hritvik.WhistlistManagementXindusAssignment.model.Users;
import com.hritvik.WhistlistManagementXindusAssignment.repository.UserRepository;
import com.hritvik.WhistlistManagementXindusAssignment.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authenticationController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;
    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
//        when(userService.createUser(any())).thenReturn(
//                new ResponseEntity<>(new ApiResponse(true, "User registered successfully"), HttpStatus.CREATED)
//        );
    }

    @Test
    public void testAuthenticateUser_ValidData() {
        // Arrange
        AuthenticationRequestDto validRequest = new AuthenticationRequestDto("username", "password");
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getName()).thenReturn("username");
        when(jwtUtil.generateToken(anyString())).thenReturn("jwtToken");

        // Act
        ResponseEntity<?> response = authenticationController.authenticateUser(validRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("jwtToken", ((AuthenticationResponse) Objects.requireNonNull(response.getBody())).getToken());
    }

   // write test case for signup
    @Test
    public void testRegisterUser_ValidData() {
        // Arrange
        UserRequestDto validRequest = new UserRequestDto("username", "password", "email", "name");
        when(userRepository.existsByUserName(anyString())).thenReturn(false);
        when(userService.createUser(any())).thenReturn(new ResponseEntity<>( HttpStatus.CREATED));

        // Act
        ResponseEntity<?> response = authenticationController.registerUser(validRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertTrue(((ApiResponse) Objects.requireNonNull(response.getBody())).isSuccess());
//        assertEquals("User registered successfully", ((ApiResponse) Objects.requireNonNull(response.getBody())).getResult());
    }


}
