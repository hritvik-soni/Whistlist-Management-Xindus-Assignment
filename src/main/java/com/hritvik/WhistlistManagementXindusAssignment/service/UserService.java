package com.hritvik.WhistlistManagementXindusAssignment.service;

import com.hritvik.WhistlistManagementXindusAssignment.dto.ApiResponse;
import com.hritvik.WhistlistManagementXindusAssignment.dto.UserRequestDto;
import com.hritvik.WhistlistManagementXindusAssignment.dto.UserUpdateRequestDto;
import com.hritvik.WhistlistManagementXindusAssignment.model.Users;
import com.hritvik.WhistlistManagementXindusAssignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

    /**
     * Creates a new user based on the provided user request DTO
     * @param userRequestDto The user request DTO containing user information
     * @return ResponseEntity containing the result of the user creation
     */
    public ResponseEntity<?> createUser(UserRequestDto userRequestDto) {
        // Create a new Users object and set its properties based on the user request DTO
        Users user = new Users();
        user.setUserName(userRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        user.setPhoneNumber(userRequestDto.getPhoneNumber());
        user.setAddress(userRequestDto.getAddress());

        // Save the user to the repository
        userRepository.save(user);

        // Return a ResponseEntity with a success message
        return new ResponseEntity<>(new ApiResponse(true, "User created successfully"), HttpStatus.CREATED);
    }


    /**
     * Update user details.
     *
     * @param username the username of the user to be updated
     * @param userUpdateDto the user update request containing the new user details
     * @return ResponseEntity containing the result of the update operation
     */
    public ResponseEntity<?> updateUser(String username, UserUpdateRequestDto userUpdateDto) {
        // Find the user by username
        Optional<Users> user = userRepository.findByUserName(username);
        // If user is not found, return 404
        if (user.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(false, "User not found"), HttpStatus.NOT_FOUND);
        }
        // Update address if provided
        if (userUpdateDto.getAddress() != null) {
            user.get().setAddress(userUpdateDto.getAddress());
        }
        // Update phone number if provided
        if (userUpdateDto.getPhoneNumber() != null) {
            user.get().setPhoneNumber(userUpdateDto.getPhoneNumber());
        }
        // Save the updated user
        userRepository.save(user.get());
        // Return success response
        return new ResponseEntity<>(new ApiResponse(true, "User updated successfully"), HttpStatus.OK);
    }

    /**
     * Deletes a user by username
     *
     * @param username the username of the user to be deleted
     * @return ResponseEntity with status and message indicating the result of the operation
     */
    public ResponseEntity<?> deleteUser(String username) {
        // Find the user by username
        Optional<Users> user = userRepository.findByUserName(username);

        // If user not found, return not found response
        if (user.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(false, "User not found"), HttpStatus.NOT_FOUND);
        }

        // Delete the user and return success response
        userRepository.delete(user.get());
        return new ResponseEntity<>(new ApiResponse(true, "User deleted successfully"), HttpStatus.OK);
    }

    /**
     * Retrieves a user by username and returns a ResponseEntity with the user information or a not found message.
     *
     * @param username the username of the user to retrieve
     * @return a ResponseEntity with the user information if found, or a not found message if not found
     */
    public ResponseEntity<?> getUsers(String username) {
        // Find user by username
        Optional<Users> user = userRepository.findByUserName(username);

        // Return ResponseEntity with user information if found, or not found message if not found
        return user.map(users -> new ResponseEntity<>(new ApiResponse(true, users), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new ApiResponse(false, "User not found"), HttpStatus.NOT_FOUND));
    }
}
