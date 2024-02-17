package com.hritvik.WhistlistManagementXindusAssignment.repository;

import com.hritvik.WhistlistManagementXindusAssignment.model.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void existsByUserName_shouldReturnTrueWhenUserExists() {
        // Arrange
        String username = "testUser";
        when(userRepository.existsByUserName(username)).thenReturn(true);

        // Act
        boolean exists = userRepository.existsByUserName(username);

        // Assert
        assertTrue(exists);
        verify(userRepository, times(1)).existsByUserName(username);
    }

    @Test
    void existsByUserName_shouldReturnFalseWhenUserDoesNotExist() {
        // Arrange
        String username = "testUser";
        when(userRepository.existsByUserName(username)).thenReturn(false);

        // Act
        boolean exists = userRepository.existsByUserName(username);

        // Assert
        assertFalse(exists);
        verify(userRepository, times(1)).existsByUserName(username);
    }

    @Test
    void findByUserName_shouldReturnUserWhenUserExists() {
        // Arrange
        String username = "testUser";
        Users user = new Users();
        when(userRepository.findByUserName(username)).thenReturn(Optional.of(user));

        // Act
        Optional<Users> optionalUser = userRepository.findByUserName(username);

        // Assert
        assertTrue(optionalUser.isPresent());
        assertEquals(user, optionalUser.get());
        verify(userRepository, times(1)).findByUserName(username);
    }

    @Test
    void findByUserName_shouldReturnEmptyOptionalWhenUserDoesNotExist() {
        // Arrange
        String username = "testUser";
        when(userRepository.findByUserName(username)).thenReturn(Optional.empty());

        // Act
        Optional<Users> optionalUser = userRepository.findByUserName(username);

        // Assert
        assertFalse(optionalUser.isPresent());
        verify(userRepository, times(1)).findByUserName(username);
    }
}