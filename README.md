# L-Delivery Partner API

L-Delivery is a fictional delivery service that needs an API to manage its partner network efficiently. This API performs CRUD operations on partners, calculates the closest partner based on a given address, and allows dynamic searches using Spring Data JPA Specifications.

## 🚀 Features

- **CRUD Operations on Partners:** Create, read, update, and delete partners, including their locations and coverage areas.
- **Closest Partner Search:** Find the nearest partner to a specific address using spatial data.
- **Dynamic Search with Filters:** Perform searches with dynamic filters using Spring Data JPA Specifications.
- **Paginated and Sorted Partner Listing:** Retrieve a list of partners with pagination and sorting options.
  
## 🛠️ Technologies

The following technologies are used in this project:

- **Java 17** - For building the application.
- **Spring Boot 3** - For rapid development with Spring.
- **Spring Data JPA** - For working with databases and dynamic queries.
- **Hibernate Spatial** - For handling geographic and spatial data.
- **SpringDoc OpenAPI** - For generating API documentation automatically.
- **PostgreSQL** - For database storage of partner data.
- **Docker** - For containerization of the app and PostgreSQL database.
  
## 🐳 Docker Setup

The easiest way to run the application is by using Docker and Docker Compose. Follow these steps:

1. **Clone the repository:**
   ```bash
   git clone https://github.com/luizgustavo-a/partners-api.git
   cd partners-api
   ```

2. **Build and start the application with Docker Compose:**
   ```bash
   docker-compose up --build
   ```

   This command will:
   - Build the Docker image for the Spring Boot application.
   - Spin up the PostgreSQL database inside a Docker container.
   - Run the Spring Boot application.

3. **Access the application:**
   - API: `http://localhost:8080`
   - Swagger API Docs: `http://localhost:8080/swagger-ui.html`

## 📚 API Documentation

The API is documented using **SpringDoc OpenAPI**. You can access the documentation at `http://localhost:8080/swagger-ui.html` after starting the application.

## 📄 API Endpoints Overview

### Create a Partner
```http
POST /partners
```
Create a new partner with required details.

### Get Partner by ID
```http
GET /partners/{id}
```
Retrieve a partner by their unique ID.

### Search for Closest Partner
```http
GET /partners/search/closest?latitude={latitude}&longitude={longitude}
```
Find the closest partner to the provided coordinates.

### Search Partners by Address
```http
GET /partners/search?latitude={latitude}&longitude={longitude}&page={page}&size={size}&sort={sort}
```
Search for partners within the coverage area of the provided coordinates with pagination support.

### Update a Partner
```http
PUT /partners
```
Update an existing partner's information.

### Delete a Partner
```http
DELETE /partners/{id}
```
Remove a partner from the database.

## 📂 Project Structure

- **`api_partners/application/`**: Contains the use cases.
- **`api_partners/domain/`**: Domain entities like Partner.
- **`api_partners/config/`**: Configurations for Spring Doc, beans and error handler.
- **`api_partners/infra/controller/`**: REST controllers for handling HTTP requests and DTOs.
- **`api_partners/infra/gateway/`**: Gateways, including data mapping and conversion logic.
- **`api_partners/infra/repositories/`**: Repository and database entities.
  
## 💾 Database

- The application uses **PostgreSQL** with **Hibernate Spatial** for storing geographic data like partner addresses and coverage areas.

## 📝 Authorship

This project was based on the [Zé Code Backend Challange](https://github.com/ab-inbev-ze-company/ze-code-challenges/blob/master/backend.md) and was developed by Luiz Almeida as a personal study.
