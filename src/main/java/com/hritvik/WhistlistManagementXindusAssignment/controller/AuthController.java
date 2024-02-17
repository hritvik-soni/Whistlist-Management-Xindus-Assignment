package com.hritvik.WhistlistManagementXindusAssignment.controller;

import com.hritvik.WhistlistManagementXindusAssignment.config.JwtUtil;
import com.hritvik.WhistlistManagementXindusAssignment.dto.ApiResponse;
import com.hritvik.WhistlistManagementXindusAssignment.dto.AuthenticationRequestDto;
import com.hritvik.WhistlistManagementXindusAssignment.dto.AuthenticationResponse;
import com.hritvik.WhistlistManagementXindusAssignment.dto.UserRequestDto;
import com.hritvik.WhistlistManagementXindusAssignment.model.Users;
import com.hritvik.WhistlistManagementXindusAssignment.repository.UserRepository;
import com.hritvik.WhistlistManagementXindusAssignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Authenticates the user with the provided authentication request data.
     *
     * @param authenticationRequestDto the authentication request data
     * @return the ResponseEntity containing the authentication response
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequestDto authenticationRequestDto) {
        // Authenticate user with the provided username and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequestDto.getUsername(), authenticationRequestDto.getPassword()));
        // Set the authenticated user's security context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Generate JWT token for the authenticated user
        String jwt = jwtUtil.generateToken(authentication.getName());
        // Return the authentication response with the generated JWT token
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }


    /**
     * Registers a user.
     *
     * @param userRequestDto the user request DTO containing user information
     * @return the response entity containing the result of the registration process
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDto userRequestDto) {

        // Check if the username already exists
        if (userRepository.existsByUserName(userRequestDto.getUsername())) {
            // Return bad request response if the username is already taken
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Username is already taken!"));
        }

        // Create the user if the username is available
        return userService.createUser(userRequestDto);
    }
}
