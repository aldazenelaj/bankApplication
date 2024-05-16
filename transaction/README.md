# Transaction Service

## Overview

This project is a transaction service built with Java 18 and Spring Boot. It provides functionalities for managing bank accounts, card applications, and related operations. The project structure is organized into various packages, each responsible for specific features of the application.

## Project Structure

The project is structured into the following packages:

1. **controller**: Contains the REST controllers for handling bank account and card application requests.
2. **dto**: Contains the Data Transfer Objects (DTOs) for bank accounts, card applications, and related entities.
3. **entity**: Contains the entity classes representing the database tables.
4. **mapper**: Contains the configuration for mapping entities to DTOs and vice versa.
5. **repository**: Contains the repository interfaces for data access.
6. **service**: Contains the service classes for business logic.

## Packages and Classes

### controller

- **BankAccountApplicationController**: Handles bank account application-related endpoints.
- **CardApplicationController**: Handles card application-related endpoints.

### dto

- **ActionCardRequestDto**: DTO for action requests on card applications.
- **ActionRequestDto**: DTO for action requests on bank account applications.
- **BankAccountApplicationDto**: DTO for bank account application data.
- **BankAccountDto**: DTO for bank account data.
- **BankDto**: DTO for bank data.
- **BankTypeDto**: DTO for bank type data.
- **CardApplicationDto**: DTO for card application data.
- **CardTypeDto**: DTO for card type data.
- **CountryDto**: DTO for country data.
- **CurrencyDto**: DTO for currency data.

### entity

- **BankAccountApplicationEntity**: Entity for bank account application data.
- **BankAccountEntity**: Entity for bank account data.
- **BankAccountTypeEntity**: Entity for bank account type data.
- **BankEntity**: Entity for bank data.
- **CardApplicationEntity**: Entity for card application data.
- **CardTypeEntity**: Entity for card type data.
- **CountryEntity**: Entity for country data.
- **CurrencyEntity**: Entity for currency data.

### mapper

- **ModelMapperConfig**: Configures the model mapper for converting between entities and DTOs.

### repository

- **IBankAccountApplicationRepository**: Repository interface for accessing bank account application data.
- **IBankAccountRepository**: Repository interface for accessing bank account data.
- **IBankAccountTypeRepository**: Repository interface for accessing bank account type data.
- **IBankRepository**: Repository interface for accessing bank data.
- **ICardApplicatonRepository**: Repository interface for accessing card application data.
- **ICardTypeRepository**: Repository interface for accessing card type data.
- **ICurrencyRepository**: Repository interface for accessing currency data.

### service

- **BankAccountApplicationService**: Manages bank account application-related operations.
- **BankAccountService**: Manages bank account-related operations.
- **CardApplicationService**: Manages card application-related operations.
- **ExtractUserInformation**: Extracts user information from security token.
- **IBANGenerator**: Generates IBAN for bank accounts.
- **TransactionApplication**: Main entry point of the application.

## Technologies Used

- **Java 18**: The programming language used for development.
- **Spring Boot**: The framework used for building the application.
- **Spring Data JPA**: Used for data access and persistence.
- **ModelMapper**: Used for mapping between entities and DTOs.

## Getting Started
