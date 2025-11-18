# Bank Account Microservice

This project implements a Bank Account Microservice using Spring Boot, following the requirements for "Activité Pratique N°1 - Développement d'un Micro-service."

## Features

- **JPA Entity**: Compte (Bank Account) with fields for id, createdAt, balance, currency, and account type
- **Repository**: Spring Data JPA repository for data access
- **DTOs and Mappers**: Separate DTOs for request/response and a mapper component
- **Service Layer**: Business logic for account management
- **REST API**: Custom RESTful endpoints for account operations
- **Spring Data REST**: Automatic REST endpoints
- **GraphQL**: API for querying accounts via GraphQL
- **Swagger Documentation**: Auto-generated API documentation
- **Data Initialization**: Sample data loaded on startup

## Technologies Used

- Spring Boot 3.3.0
- Spring Data JPA
- H2 Database
- Lombok
- Spring Data REST
- Spring for GraphQL
- Spring Doc OpenAPI

## Getting Started

### Prerequisites

- Java JDK 21
- Maven 3.8+

### Running the Application

1. Clone the repository:
   ```bash
   git clone https://github.com/Youss2f/Jee-Bank-Micro-service.git
   cd Jee-Bank-Micro-service
   ```

2. Build the project:
   ```bash
   mvn clean compile
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

4. Access the endpoints:
   - Manual REST API: http://localhost:8080/api/comptes
   - Spring Data REST: http://localhost:8080/comptes
   - Swagger UI: http://localhost:8080/swagger-ui.html
   - GraphQL Playground: http://localhost:8080/graphiql
   - H2 Console: http://localhost:8080/h2-console

## API Endpoints

### Manual REST API

- GET /api/comptes - Get all accounts
- GET /api/comptes/{id} - Get account by ID
- POST /api/comptes - Create new account

Example POST request:
```
POST /api/comptes
Content-Type: application/json

{
    "balance": 5000.0,
    "currency": "EUR",
    "type": "SAVING_ACCOUNT"
}
```

### GraphQL Queries

Example query:
```
query {
  accountsList {
    id
    balance
    currency
    type
  }
}
```

## Project Structure

- `entities/` - JPA entities
- `repository/` - Data access layer
- `dto/` - Data Transfer Objects
- `mappers/` - Object mapping components
- `service/` - Business logic layer
- `web/` - Web controllers (REST and GraphQL)
