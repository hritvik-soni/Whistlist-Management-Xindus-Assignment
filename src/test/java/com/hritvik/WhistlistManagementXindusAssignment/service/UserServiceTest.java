package com.hritvik.WhistlistManagementXindusAssignment.service;

import com.hritvik.WhistlistManagementXindusAssignment.dto.ApiResponse;
import com.hritvik.WhistlistManagementXindusAssignment.dto.UserRequestDto;
import com.hritvik.WhistlistManagementXindusAssignment.dto.UserUpdateRequestDto;
import com.hritvik.WhistlistManagementXindusAssignment.model.Users;
import com.hritvik.WhistlistManagementXindusAssignment.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_shouldReturnCreatedResponse() {
        // Arrange
        UserRequestDto userRequestDto = new UserRequestDto();
        Users user = new Users();
        when(userRepository.save(any(Users.class))).thenReturn(user);

        // Act
        ResponseEntity<?> response = userService.createUser(userRequestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertInstanceOf(ApiResponse.class, response.getBody());
        ApiResponse apiResponse = (ApiResponse) response.getBody();
        assertTrue(apiResponse.isSuccess());
        assertEquals("User created successfully", apiResponse.getResult());
        verify(userRepository, times(1)).save(any(Users.class));
    }

    @Test
    void updateUser_shouldReturnOkResponseWhenUserExists() {
        // Arrange
        String username = "testUser";
        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto();
        Users user = new Users();
        Optional<Users> optionalUser = Optional.of(user);
        when(userRepository.findByUserName(username)).thenReturn(optionalUser);
        when(userRepository.save(any(Users.class))).thenReturn(user);

        // Act
        ResponseEntity<?> response = userService.updateUser(username, userUpdateRequestDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(ApiResponse.class, response.getBody());
        ApiResponse apiResponse = (ApiResponse) response.getBody();
        assertTrue(apiResponse.isSuccess());
        assertEquals("User updated successfully", apiResponse.getResult());
        verify(userRepository, times(1)).findByUserName(username);
        verify(userRepository, times(1)).save(any(Users.class));
    }

    @Test
    void updateUser_shouldReturnNotFoundResponseWhenUserDoesNotExist() {
        // Arrange
        String username = "testUser";
        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto();
        Optional<Users> optionalUser = Optional.empty();
        when(userRepository.findByUserName(username)).thenReturn(optionalUser);

        // Act
        ResponseEntity<?> response = userService.updateUser(username, userUpdateRequestDto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertInstanceOf(ApiResponse.class, response.getBody());
        ApiResponse apiResponse = (ApiResponse) response.getBody();
        assertFalse(apiResponse.isSuccess());
        assertEquals("User not found", apiResponse.getResult());
        verify(userRepository, times(1)).findByUserName(username);
        verify(userRepository, never()).save(any(Users.class));
    }

    @Test
    void deleteUser_shouldReturnOkResponseWhenUserExists() {
        // Arrange
        String username = "testUser";
        Users user = new Users();
        Optional<Users> optionalUser = Optional.of(user);
        when(userRepository.findByUserName(username)).thenReturn(optionalUser);

        // Act
        ResponseEntity<?> response = userService.deleteUser(username);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(ApiResponse.class, response.getBody());
        ApiResponse apiResponse = (ApiResponse) response.getBody();
        assertTrue(apiResponse.isSuccess());
        assertEquals("User deleted successfully", apiResponse.getResult());
        verify(userRepository, times(1)).findByUserName(username);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void deleteUser_shouldReturnNotFoundResponseWhenUserDoesNotExist() {
        // Arrange
        String username = "testUser";
        Optional<Users> optionalUser = Optional.empty();
        when(userRepository.findByUserName(username)).thenReturn(optionalUser);

        // Act
        ResponseEntity<?> response = userService.deleteUser(username);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertInstanceOf(ApiResponse.class, response.getBody());
        ApiResponse apiResponse = (ApiResponse) response.getBody();
        assertFalse(apiResponse.isSuccess());
        assertEquals("User not found", apiResponse.getResult());
        verify(userRepository, times(1)).findByUserName(username);
        verify(userRepository, never()).delete(any(Users.class));
    }

    @Test
    void getUsers_shouldReturnOkResponseWhenUserExists() {
        // Arrange
        String username = "testUser";
        Users user = new Users();
        Optional<Users> optionalUser = Optional.of(user);
        when(userRepository.findByUserName(username)).thenReturn(optionalUser);

        // Act
        ResponseEntity<?> response = userService.getUsers(username);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(ApiResponse.class, response.getBody());
        ApiResponse apiResponse = (ApiResponse) response.getBody();
        assertTrue(apiResponse.isSuccess());
        assertEquals(user, apiResponse.getResult());
        verify(userRepository, times(1)).findByUserName(username);
    }

    @Test
    void getUsers_shouldReturnNotFoundResponseWhenUserDoesNotExist() {
        // Arrange
        String username = "testUser";
        Optional<Users> optionalUser = Optional.empty();
        when(userRepository.findByUserName(username)).thenReturn(optionalUser);

        // Act
        ResponseEntity<?> response = userService.getUsers(username);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertInstanceOf(ApiResponse.class, response.getBody());
        ApiResponse apiResponse = (ApiResponse) response.getBody();
        assertFalse(apiResponse.isSuccess());
        assertEquals("User not found", apiResponse.getResult());
        verify(userRepository, times(1)).findByUserName(username);
    }
}