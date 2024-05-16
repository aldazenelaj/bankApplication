# Authentication Service

## Overview

This project is an authentication service built with Java 18, Spring Boot, and PostgreSQL. It provides functionalities for user authentication, registration, and role management. The project structure is organized into various packages, each responsible for specific features of the application.

## Project Structure

The project is structured into the following packages:

1. **auth**: Contains the JWT authentication filter.
2. **config**: Contains configuration classes for the application and security.
3. **controller**: Contains the REST controllers for handling authentication and user-related requests.
4. **entity**: Contains the entity classes representing the database tables.
5. **exception**: Contains the exception handling classes.
6. **repository**: Contains the repository interfaces for data access.
7. **request**: Contains the request DTOs (Data Transfer Objects).
8. **response**: Contains the response DTOs.
9. **service**: Contains the service classes for business logic.

## Packages and Classes

### auth

- **JwtAuthenticationFilter**: This filter intercepts HTTP requests to validate JWT tokens.

### config

- **ApplicationConfiguration**: Configures application-specific settings.
- **SecurityConfiguration**: Configures Spring Security settings, including authentication and authorization mechanisms.

### controller

- **AuthenticationController**: Handles authentication-related endpoints such as login.
- **UserController**: Handles user-related endpoints such as user registration and deletion.

### entity

- **Role**: Represents the role entity in the database.
- **User**: Represents the user entity in the database.

### exception

- **ExceptionErrorHandler**: Handles exceptions and returns appropriate error responses.

### repository

- **RoleRepository**: Repository interface for accessing role data.
- **UserRepository**: Repository interface for accessing user data.

### request

- **LoginRequest**: DTO for login requests.
- **RegisterUserRequest**: DTO for user registration requests.
- **RoleDto**: DTO for role information.

### response

- **ErrorResult**: DTO for error responses.
- **LoginResult**: DTO for login responses.
- **UserResponse**: DTO for user responses.

### service

- **AuthenticationService**: Manages authentication logic.
- **JwtService**: Manages JWT token creation and validation.
- **LoginService**: Manages login logic.
- **UserService**: Manages user-related operations such as registration and deletion.

## Technologies Used

- **Java 18**: The programming language used for development.
- **Spring Boot**: The framework used for building the application.
- **Spring Security**: Used for securing the application and managing authentication and authorization.
- **PostgreSQL**: The database used for storing user and role information.
- **JWT (JSON Web Tokens)**: Used for token-based authentication.

