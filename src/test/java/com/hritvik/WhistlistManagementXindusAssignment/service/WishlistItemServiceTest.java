package com.hritvik.WhistlistManagementXindusAssignment.service;

import com.hritvik.WhistlistManagementXindusAssignment.dto.ApiResponse;
import com.hritvik.WhistlistManagementXindusAssignment.dto.WishlistItemRequestDto;
import com.hritvik.WhistlistManagementXindusAssignment.dto.WishlistItemResponseDto;
import com.hritvik.WhistlistManagementXindusAssignment.model.Users;
import com.hritvik.WhistlistManagementXindusAssignment.model.WishlistItem;
import com.hritvik.WhistlistManagementXindusAssignment.repository.UserRepository;
import com.hritvik.WhistlistManagementXindusAssignment.repository.WishlistItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class WishlistItemServiceTest {

    @Mock
    private WishlistItemRepository wishlistItemRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private WishlistItemService wishlistItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserWishlistItems() {
        // Mock user
        Users user = new Users();
        user.setId(1L);
        user.setUserName("testUser");

        // Mock wishlist items
        WishlistItem wishlistItem1 = new WishlistItem();
        wishlistItem1.setId(1L);
        wishlistItem1.setName("Item 1");
        wishlistItem1.setDescription("Description 1");
        wishlistItem1.setUser(user);


        List<WishlistItem> wishlistItems = new ArrayList<>();
        wishlistItems.add(wishlistItem1);


        // Mock repository methods
        when(userRepository.findByUserName(eq("testUser"))).thenReturn(Optional.of(user));
        when(wishlistItemRepository.findByUserId(eq(1L))).thenReturn(wishlistItems);

        // Call the service method
        ResponseEntity<?> responseEntity = wishlistItemService.getUserWishlistItems("testUser");

        // Verify the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(((ApiResponse) Objects.requireNonNull(responseEntity.getBody())).isSuccess());

        // Get the result from the response
        Object result = ((ApiResponse) responseEntity.getBody()).getResult();
        assertInstanceOf(List.class, result); // Ensure the result is a List

        List<?> resultList = (List<?>) result;
        assertEquals(1, resultList.size()); // Check the size of the list

        // Iterate over the list and verify each item
        for (Object item : resultList) {
            assertInstanceOf(WishlistItemResponseDto.class, item); // Ensure each item is of type WishlistItemResponseDto
            WishlistItemResponseDto dto = (WishlistItemResponseDto) item;
            // Verify the properties of each dto if needed
            // match input with output
            assertEquals("Item 1", dto.getName());
            assertEquals("Description 1", dto.getDescription());
            assertEquals("testUser", dto.getUserName());
        }
    }

    @Test
    void testCreateWishlistItem() {
        // Mock user
        Users user = new Users();
        user.setId(1L);
        user.setUserName("testUser");

        // Mock request DTO
        WishlistItemRequestDto wishlistItemDto = new WishlistItemRequestDto();
        wishlistItemDto.setName("Item 1");
        wishlistItemDto.setDescription("Description 1");

        // Mock repository methods
        when(userRepository.findByUserName(eq("testUser"))).thenReturn(Optional.of(user));
        when(wishlistItemRepository.save(any(WishlistItem.class))).thenReturn(new WishlistItem());

        // Call the service method
        ResponseEntity<?> responseEntity = wishlistItemService.createWishlistItem("testUser", wishlistItemDto);

        // Verify the response
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertTrue(((ApiResponse) Objects.requireNonNull(responseEntity.getBody())).isSuccess());
        assertEquals("Wishlist item created successfully", ((ApiResponse) responseEntity.getBody()).getResult());
    }

    @Test
    void testDeleteWishlistItem() {
        // Mock user
        Users user = new Users();
        user.setId(1L);
        user.setUserName("testUser");

        // Mock wishlist item
        WishlistItem wishlistItem = new WishlistItem();
        wishlistItem.setId(1L);
        wishlistItem.setName("Item 1");
        wishlistItem.setDescription("Description 1");
        wishlistItem.setUser(user);

        // Mock repository methods
        when(userRepository.findByUserName(eq("testUser"))).thenReturn(Optional.of(user));
        when(wishlistItemRepository.findById(eq(1L))).thenReturn(Optional.of(wishlistItem));

        // Mock the behavior of deleteById without returning anything
        doNothing().when(wishlistItemRepository).deleteById(eq(1L));

        // Call the service method
        ResponseEntity<?> responseEntity = wishlistItemService.deleteWishlistItem(1L, "testUser");

        // Verify the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(((ApiResponse) Objects.requireNonNull(responseEntity.getBody())).isSuccess());
        assertEquals("Wishlist item deleted successfully", ((ApiResponse) responseEntity.getBody()).getResult());
    }

}