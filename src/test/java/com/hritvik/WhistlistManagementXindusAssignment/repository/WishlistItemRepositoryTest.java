package com.hritvik.WhistlistManagementXindusAssignment.repository;


import com.hritvik.WhistlistManagementXindusAssignment.model.WishlistItem;
import com.hritvik.WhistlistManagementXindusAssignment.model.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WishlistItemRepositoryTest {

    @Mock
    private WishlistItemRepository wishlistItemRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByUserId_shouldReturnListOfWishlistItems() {
        // Arrange
        Long userId = 1L;
        List<WishlistItem> wishlistItems = new ArrayList<>();
        when(wishlistItemRepository.findByUserId(userId)).thenReturn(wishlistItems);

        // Act
        List<WishlistItem> result = wishlistItemRepository.findByUserId(userId);

        // Assert
        assertEquals(wishlistItems, result);
        verify(wishlistItemRepository, times(1)).findByUserId(userId);
    }

    @Test
    void findByUserId_shouldReturnEmptyListWhenNoItemsExist() {
        // Arrange
        Long userId = 1L;
        when(wishlistItemRepository.findByUserId(userId)).thenReturn(new ArrayList<>());

        // Act
        List<WishlistItem> result = wishlistItemRepository.findByUserId(userId);

        // Assert
        assertTrue(result.isEmpty());
        verify(wishlistItemRepository, times(1)).findByUserId(userId);
    }

    @Test
    void findByIdAndUser_shouldReturnWishlistItemWhenItemExists() {
        // Arrange
        Long itemId = 1L;
        Users user = new Users();
        WishlistItem wishlistItem = new WishlistItem();
        wishlistItem.setUser(user);
        when(wishlistItemRepository.findByIdAndUser(itemId, user)).thenReturn(Optional.of(wishlistItem));

        // Act
        Optional<WishlistItem> result = wishlistItemRepository.findByIdAndUser(itemId, user);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(wishlistItem, result.get());
        verify(wishlistItemRepository, times(1)).findByIdAndUser(itemId, user);
    }

    @Test
    void findByIdAndUser_shouldReturnEmptyOptionalWhenItemDoesNotExist() {
        // Arrange
        Long itemId = 1L;
        Users user = new Users();
        when(wishlistItemRepository.findByIdAndUser(itemId, user)).thenReturn(Optional.empty());

        // Act
        Optional<WishlistItem> result = wishlistItemRepository.findByIdAndUser(itemId, user);

        // Assert
        assertFalse(result.isPresent());
        verify(wishlistItemRepository, times(1)).findByIdAndUser(itemId, user);
    }
}