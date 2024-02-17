package com.hritvik.WhistlistManagementXindusAssignment.controller;

import com.hritvik.WhistlistManagementXindusAssignment.dto.WishlistItemRequestDto;
import com.hritvik.WhistlistManagementXindusAssignment.model.WishlistItem;
import com.hritvik.WhistlistManagementXindusAssignment.service.WishlistItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class WishlistItemControllerTest {

    @Mock
    private WishlistItemService wishlistItemService;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private WishlistItemController wishlistItemController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserWishlistItemsForValidUsername() {
        // Arrange
        String username = "testUser";
        List<WishlistItem> wishlistItems = new ArrayList<>();
        when(userDetails.getUsername()).thenReturn(username);
        when(wishlistItemService.getUserWishlistItems(username)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        // Act
        ResponseEntity<?> response = wishlistItemController.getUserWishlistItems(userDetails);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(wishlistItemService).getUserWishlistItems(username);
    }

    @Test
    void testCreateWishlistItem() {
        // Arrange
        String username = "testUser";
        WishlistItemRequestDto wishlistItemRequestDto = new WishlistItemRequestDto("Item Name", "Item Description");
        when(userDetails.getUsername()).thenReturn(username);
        when(wishlistItemService.createWishlistItem(username, wishlistItemRequestDto)).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

        // Act
        ResponseEntity<?> response = wishlistItemController.createWishlistItem(userDetails, wishlistItemRequestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(wishlistItemService).createWishlistItem(username, wishlistItemRequestDto);
    }

    @Test
    void testDeleteWishlistItem() {
        // Arrange
        Long itemId = 1L;
        String username = "testUser";
        when(userDetails.getUsername()).thenReturn(username);
        when(wishlistItemService.deleteWishlistItem(itemId, username)).thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        // Act
        ResponseEntity<?> response = wishlistItemController.deleteWishlistItem(userDetails, itemId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(wishlistItemService, times(1)).deleteWishlistItem(itemId, username);
    }
}