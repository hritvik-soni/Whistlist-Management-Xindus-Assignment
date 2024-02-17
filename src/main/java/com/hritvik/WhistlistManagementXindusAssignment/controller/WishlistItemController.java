package com.hritvik.WhistlistManagementXindusAssignment.controller;

import com.hritvik.WhistlistManagementXindusAssignment.dto.WishlistItemRequestDto;
import com.hritvik.WhistlistManagementXindusAssignment.model.Users;
import com.hritvik.WhistlistManagementXindusAssignment.model.WishlistItem;
import com.hritvik.WhistlistManagementXindusAssignment.service.WishlistItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistItemController {

    @Autowired
    private WishlistItemService wishlistItemService;

    /**
     * Retrieves the wishlist items for the authenticated user.
     *
     * @param userDetails the user details for the authenticated user
     * @return a list of wishlist items for the authenticated user
     */
    @GetMapping
    public ResponseEntity<?> getUserWishlistItems(@AuthenticationPrincipal UserDetails userDetails) {
        // Get the username from user details
        String username = userDetails.getUsername();
        // Retrieve the wishlist items for the authenticated user
        return wishlistItemService.getUserWishlistItems(username);
    }

    /**
     * Creates a new wishlist item for the given user.
     *
     * @param userDetails             the user details
     * @param wishlistItemRequestDto  the wishlist item request data transfer object
     * @return                       the ResponseEntity containing the created wishlist item
     */
    @PostMapping
    public ResponseEntity<?> createWishlistItem(@AuthenticationPrincipal UserDetails userDetails, @RequestBody WishlistItemRequestDto wishlistItemRequestDto) {
        // Get the username from user details
        String username = userDetails.getUsername();
        // Create a new wishlist item and return the response
        return  wishlistItemService.createWishlistItem(username, wishlistItemRequestDto);
    }
    /**
     * Deletes a wishlist item by ID.
     *
     * @param userDetails - the user details
     * @param id - the ID of the wishlist item to be deleted
     * @return an OK response with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWishlistItem(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") Long id) {
        // Delegate the deletion of the wishlist item to the wishlistItemService
        return wishlistItemService.deleteWishlistItem(id, userDetails.getUsername());
    }
}
