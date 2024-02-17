# Wishlist Management Backend

## Overview

This project is a backend solution for an e-commerce platform's wishlist management feature. It allows users to create and manage their wishlists securely. The backend is implemented using Spring Boot and integrates with a relational database using Spring Data JPA. Key functionalities include user authentication, wishlist management, database integration, unit testing, and comprehensive documentation.

## Features

### User Authentication

- **Sign Up**: Users can securely sign up for an account.
- **Sign In**: Registered users can log in securely to access their accounts.

### Wishlist Management

- **Retrieve Wishlist**: Users can retrieve their wishlist via a GET endpoint.
- **Add Wishlist Item**: Users can add items to their wishlist via a POST endpoint.
- **Remove Wishlist Item**: Users can remove items from their wishlist via a DELETE endpoint.


## Getting Started

To run the application locally, follow these steps:

1. Clone the repository: `git clone <repository-url>`
2. Navigate to the project directory: `cd wishlist-management-backend`
3. Set up Database in application.properties 
4. Build the project: `./mvnw clean package`
5. Run the application: `./mvnw spring-boot:run`

For running unit tests:

- Execute: `./mvnw test`

## API Endpoints

### Authentication

- `POST /api/auth/signup`: Sign up a new user.
    - Request Payload:
      ```json
      {
        "username": "user123",
        "email": "user@example.com",
        "password": "password123",
        "name": "user",
        "phoneNumber": "7777777777",
        "address": "user address"
      }
      ```
    - Response: User details with authentication token.

- `POST /api/auth/signin`: Log in an existing user.
    - Request Payload:
      ```json
      {
        "username": "user123",
        "password": "password123"
      }
      ```
    - Response: User details with authentication token.

### Wishlist Management

- `GET /api/wishlists`: Retrieve user's wishlist.
    - Requires authentication token in the header.

- `POST /api/wishlists`: Add a new item to the wishlist.
    - Request Payload:
      ```json
      {
        "name": "Product Name",
        "description": "Product Description",
        "price": 99.99
      }
      ```
    - Requires authentication token in the header.

- `DELETE /api/wishlists/{id}`: Remove an item from the wishlist by ID.
    - Requires authentication token in the header.

## Technologies Used

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Maven
- MySQL

## 
Happy Coding!

