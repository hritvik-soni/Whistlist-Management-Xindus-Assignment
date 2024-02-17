package com.hritvik.WhistlistManagementXindusAssignment.service;

import com.hritvik.WhistlistManagementXindusAssignment.dto.ApiResponse;
import com.hritvik.WhistlistManagementXindusAssignment.dto.WishlistItemRequestDto;
import com.hritvik.WhistlistManagementXindusAssignment.dto.WishlistItemResponseDto;
import com.hritvik.WhistlistManagementXindusAssignment.model.Users;
import com.hritvik.WhistlistManagementXindusAssignment.model.WishlistItem;
import com.hritvik.WhistlistManagementXindusAssignment.repository.UserRepository;
import com.hritvik.WhistlistManagementXindusAssignment.repository.WishlistItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistItemService {

    @Autowired
    private WishlistItemRepository wishlistItemRepository;
    @Autowired
    UserRepository userRepository;

    /**
     * Retrieves the wishlist items for a user.
     * @param username The username of the user
     * @return ResponseEntity containing the list of wishlist items or an error message
     */
    public ResponseEntity<?> getUserWishlistItems(String username) {
        // Retrieve the user by username
        Optional<Users> userOptional = userRepository.findByUserName(username);

        // Check if the user is present in the repository
        if (userOptional.isEmpty()) {
            // Return a response indicating that the user was not found
            return new ResponseEntity<>(new ApiResponse(false, "User not found"), HttpStatus.NOT_FOUND);
        }

        // Retrieve the wishlist items for the user
        List<WishlistItem> wishlistItems = wishlistItemRepository.findByUserId(userOptional.get().getId());

        // If no wishlist items are found, return an error response
        if (wishlistItems.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(false, "No wishlist items found"), HttpStatus.BAD_REQUEST);
        }

        // Map the wishlist items to DTOs for response
        List<WishlistItemResponseDto> wishlistItemResponseDtos = wishlistItems.stream().map(wishlistItem -> {
            WishlistItemResponseDto wishlistItemResponseDto = new WishlistItemResponseDto();
            wishlistItemResponseDto.setDescription(wishlistItem.getDescription());
            wishlistItemResponseDto.setName(wishlistItem.getName());
            wishlistItemResponseDto.setPrice(wishlistItem.getPrice());
            wishlistItemResponseDto.setUserName(username);
            return wishlistItemResponseDto;
        }).toList();

        // Return the list of wishlist items
        return new ResponseEntity<>(new ApiResponse(true, wishlistItemResponseDtos), HttpStatus.OK);
    }


    /**
     * Creates a new wishlist item for the specified user.
     *
     * @param username the username of the user creating the wishlist item
     * @param wishlistItemDto the request body containing the details of the wishlist item
     * @return a ResponseEntity with a success message if the wishlist item is created successfully
     */
    public ResponseEntity<?> createWishlistItem(String username, WishlistItemRequestDto wishlistItemDto) {
       Optional<Users> userOptional = userRepository.findByUserName(username);

        if (userOptional.isEmpty()) {
            // Return a response indicating that the user was not found
            return new ResponseEntity<>(new ApiResponse(false, "User not found"), HttpStatus.NOT_FOUND);
        }
        // Create a new WishlistItem object
        WishlistItem wishlistItem = new WishlistItem();

        // Set the description and name of the wishlist item from the request body
        wishlistItem.setDescription(wishlistItemDto.getDescription());
        wishlistItem.setName(wishlistItemDto.getName());
        wishlistItem.setPrice(wishlistItemDto.getPrice());

        // Set the user for the wishlist item
        wishlistItem.setUser(userOptional.get());

        // Save the wishlist item to the database
        wishlistItemRepository.save(wishlistItem);

        // Return a ResponseEntity with a success message
        return new ResponseEntity<>(new ApiResponse(true, "Wishlist item created successfully"), HttpStatus.CREATED);
    }

    /**
     * Deletes a wishlist item for the given id and username.
     * If the item is not found, returns HTTP 404.
     * If the user is not authorized to delete the item, returns HTTP 403.
     * If the deletion is successful, returns HTTP 200.
     *
     * @param id The id of the wishlist item to delete
     * @param username The username of the user attempting to delete the wishlist item
     * @return ResponseEntity containing the result of the deletion
     */
    public ResponseEntity<?> deleteWishlistItem(Long id, String username) {
        Optional<Users> userOptional = userRepository.findByUserName(username);

        if (userOptional.isEmpty()) {
            // Return a response indicating that the user was not found
            return new ResponseEntity<>(new ApiResponse(false, "User not found"), HttpStatus.NOT_FOUND);
        }
        Optional<WishlistItem> wishlistItemOptional = wishlistItemRepository.findById(id);
        // Check if the wishlist item exists
        if (wishlistItemOptional.isEmpty()) {
            // Return a response indicating that the wishlist item was not found
            return new ResponseEntity<>(new ApiResponse(false, "Wishlist item not found"), HttpStatus.NOT_FOUND);
        }

        // Check if the user is authorized to delete the wishlist item
        if (!userOptional.get().getId().equals(wishlistItemOptional.get().getUser().getId())) {
            return new ResponseEntity<>(new ApiResponse(false, "You are not authorized to delete this wishlist item"), HttpStatus.FORBIDDEN);
        }

        // Delete the wishlist item and return success message
        wishlistItemRepository.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(true, "Wishlist item deleted successfully"), HttpStatus.OK);
    }
}

