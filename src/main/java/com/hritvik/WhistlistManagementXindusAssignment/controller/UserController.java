package com.hritvik.WhistlistManagementXindusAssignment.controller;

import com.hritvik.WhistlistManagementXindusAssignment.dto.UserUpdateRequestDto;
import com.hritvik.WhistlistManagementXindusAssignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
   private UserService userService;


    /**
     * Updates a user.
     *
     * @param userDetails the user details
     * @param userUpdateDto the user update request data transfer object
     * @return the response entity
     */
    @PutMapping
    public ResponseEntity<?> updateUser(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserUpdateRequestDto userUpdateDto
    ) {
        // Call the userService to update the user with the given details
        return userService.updateUser(userDetails.getUsername(), userUpdateDto);
    }

    /**
     * Deletes a user.
     *
     * @param userDetails the user details to be deleted
     * @return ResponseEntity indicating the result of the delete operation
     */
    @DeleteMapping
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
        // Call the userService to delete the user
        return userService.deleteUser(userDetails.getUsername());
    }

    /**
     * Retrieves users based on the provided ID.
     *
     * @param userDetails the authenticated user details
     * @return a ResponseEntity containing the retrieved user
     */
    @GetMapping()
    public ResponseEntity<?> getUsers(@AuthenticationPrincipal UserDetails userDetails) {
        // Call the userService to retrieve the user
        return userService.getUsers(userDetails.getUsername());
    }
}
