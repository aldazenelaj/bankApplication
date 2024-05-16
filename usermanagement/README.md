# User Management Service

## Overview

This project is a user management service built with Java 18 and Spring Boot. It provides functionalities for managing users and roles within the application. The project structure is organized into various packages, each responsible for specific features of the application.

## Project Structure

The project is structured into the following packages:

1. **controller**: Contains the REST controllers for handling user-related requests.
2. **dto**: Contains the Data Transfer Objects (DTOs) for user and role data.
3. **entity**: Contains the entity classes representing the database tables.
4. **external**: Contains classes for external service interactions.
5. **mapper**: Contains the configuration for mapping entities to DTOs and vice versa.
6. **repository**: Contains the repository interfaces for data access.
7. **service**: Contains the service classes for business logic.
8. **validation**: Contains validation classes for checking permissions.
9. **resources**: Contains application resources.

## Packages and Classes

### controller

- **UserController**: Handles user-related endpoints such as user registration, update, and deletion.

### dto

- **RoleDto**: Data Transfer Object for role information.
- **UserDto**: Data Transfer Object for user information.

### entity

- **Role**: Represents the role entity in the database.
- **User**: Represents the user entity in the database.

### external

- **ApiService**: Handles interactions with external services.

### mapper

- **ModelMapperConfig**: Configures the model mapper for converting between entities and DTOs.

### repository

- **RoleRepository**: Repository interface for accessing role data.
- **UserRepository**: Repository interface for accessing user data.

### service

- **UserService**: Manages user-related operations such as registration, update, and deletion.

### validation

- **CheckPermission**: Validates user permissions for certain actions.

### Application Class

- **UsermanagementApplication**: The main entry point of the application.

## Technologies Used

- **Java 18**: The programming language used for development.
- **Spring Boot**: The framework used for building the application.
- **Spring Data JPA**: Used for data access and persistence.
- **ModelMapper**: Used for mapping between entities and DTOs.
