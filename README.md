> 🇺🇿 O‘zbekcha versiya: [README_UZ.md](README_UZ.md)

# 📞 PhoneBook — Java Backend Portfolio (Production-Ready)

> A Java backend project demonstrating clean architecture, security, testing, and real-world delivery practices using CI/CD and Docker.

## TL;DR

Java backend portfolio project focused on:

- Clean, layered backend architecture (Controller → Service → Repository)
- Spring Boot–based REST API design
- Secure authentication and authorization with Spring Security and JWT
- Backend-focused testing and code quality practices
- Containerized runtime using Docker Compose for consistent environments
- Automated build and delivery using CI/CD, with live updates handled transparently

Business logic is intentionally simple to keep the emphasis on **backend design, security, and maintainability**.


## 🔗 Quick Access

- 🌍 **Live Demo:** http://44.200.65.144
  - **Demo users:**
    - Admin: `admin / admin`
    - User: `user / user`

- 📘 **API Documentation (Swagger):** http://44.200.65.144:8080/swagger-ui/index.html
- 🐳 **Docker Image:** docker.io/asanovbekzod/phonebook:latest
- 🔄 **CI/CD Pipeline: GitHub Actions — automated test, build, Docker image publishing, and server deployment on every push to main

## 🎯 Project Purpose

This project was created as a **Java backend portfolio project**, with the main focus on **backend architecture, security, testing, and code quality**.

The business logic is intentionally kept simple in order to clearly demonstrate how a **real-world Java backend service** is designed and maintained using the **Spring ecosystem**.

Through this project, I demonstrate my ability to:
- design a **clean, layered Java backend architecture** (Controller → Service → Repository)
- work confidently with **Spring Boot, Spring Security, and Spring Data JPA**
- implement **authentication and authorization** using JWT
- apply **best practices** such as DTOs, validation, and centralized exception handling
- write and run **automated tests** to ensure application stability
- integrate CI/CD automation and **Docker Compose** as part of the development workflow

CI/CD automation and Docker Compose are used to reflect how modern Java backend services are typically developed, tested, and run across environments.

## 🚀 Tech Stack

### Backend Core
- **Java 17** — main programming language
- **Spring Boot** — application framework
- **Spring Web** — REST API development
- **Spring Data JPA (Hibernate)** — data persistence layer
- **PostgreSQL** — relational database

### Security
- **Spring Security** — authentication & authorization
- **JWT (JSON Web Token)** — stateless security mechanism
- **BCrypt** — password hashing

### Testing & Quality
- **JUnit** — unit testing
- **Validation API** — request and data validation
- **Global Exception Handling** — consistent error responses

### Build & Configuration
- **Maven** — dependency management & build tool
- **Environment Variables** — externalized configuration

### Containerization & Delivery
- **Docker** — containerized application packaging
- **Docker Compose** — multi-container setup (backend + database + frontend)
- **GitHub Actions** — automated testing, build, and deployment

## 🧠 Architecture

The application follows a **layered Java backend architecture**, commonly used in real-world Spring Boot applications.  
Each layer has a clearly defined responsibility, which improves **maintainability, testability, and scalability**.

### Architectural Layers

**Controller Layer**
- Exposes REST API endpoints
- Handles request and response mapping
- Performs input validation
- Delegates business logic to the service layer

**Service Layer**
- Contains core business logic
- Coordinates application workflows
- Enforces domain rules
- Acts as the main interaction point between controllers and repositories

**Repository Layer**
- Handles data persistence using Spring Data JPA
- Abstracts database access logic
- Provides clean and declarative data access methods

### Request Flow

Client Request → Controller → Service → Repository → Database

### Security Architecture

Security is implemented using **Spring Security** with a **JWT-based authentication flow**:

1. User authenticates using credentials  
2. A JWT token is generated and returned  
3. Subsequent requests include the token in the **Authorization** header  
4. A custom security filter validates the token  
5. Authorized requests are allowed to access protected endpoints  

This approach enables **stateless authentication**, which is well-suited for modern RESTful backend services.

### Supporting Components

- **DTOs** — decouple API contracts from persistence models  
- **Validation** — ensures data correctness at the application boundary  
- **Global Exception Handling** — provides consistent and predictable error responses  
- **Configuration Classes** — centralize security and application setup  

Overall, this architecture reflects how **real-world Java backend applications** are structured and maintained.

## 🔄 CI/CD Pipeline

The project uses an **automated CI/CD pipeline** to ensure code quality and seamless delivery of backend changes.

The pipeline is implemented using **GitHub Actions** and supports the backend development workflow by automating testing, build, and deployment-related steps.

### Pipeline Trigger

The pipeline is triggered automatically on:
- every push to the `main` branch

Each change is validated and propagated without manual intervention.

### Continuous Integration (CI)

The CI stage focuses on code correctness and stability:

- source code is checked out
- the Java environment is set up
- automated tests are executed (`mvn test`)
- the application is built (`mvn package`)

If any step fails, the pipeline stops and the change is not promoted further.

### Continuous Delivery (CD)

After a successful CI stage:

- a Docker image is built from the application
- the image is published to a container registry

On the server side, **Watchtower** monitors running containers and automatically pulls the latest image when a new version is available.

This allows the application to be **updated automatically** without manual redeployment.

### Deployment Flow

1. Code is pushed to `main`
2. CI pipeline runs tests and builds the application
3. A new Docker image is published
4. Watchtower detects the updated image
5. The running container is restarted with the new version

### Purpose of Automation

The CI/CD setup is used to:
- ensure every change is tested before release
- keep the running application in sync with the codebase
- minimize manual deployment steps
- reflect a modern Java backend delivery workflow

The automation supports backend development while keeping the focus on **application architecture and code quality**.

## 🐳 Docker & Containerization

The application is fully containerized to ensure a consistent and reproducible runtime environment across local development and production.

Docker is used to package and run the system in a predictable way, while Docker Compose orchestrates the core application services required for runtime.

## Container Setup

The project uses Docker Compose to manage the following services:

### Backend Application
- Spring Boot application running inside a Docker container  
- Exposes REST APIs  
- Connects to the database via an internal Docker network  

### Frontend Application
- Runs as a separate container  
- Serves as a client for the backend APIs  
- Communicates with the backend over the internal Docker network  

### PostgreSQL Database
- Runs as an isolated container  
- Uses Docker volumes for persistent storage  
- Accessed exclusively by the backend service  

### Automatic Updates (Server-Level)

Container update automation is handled **outside of Docker Compose**, directly on the server level.

A Watchtower service is configured on the server to:
- monitor running containers
- detect newly published Docker images
- automatically restart containers with updated versions

This approach keeps the Docker Compose setup focused on application runtime, while update automation is handled independently at the infrastructure level.

Overall, this setup reflects a real-world backend-oriented environment, where the backend remains the core component, and deployment automation is handled transparently without complicating the application stack.

### Local Development

Docker Compose allows the entire stack (backend, frontend, database) to be started locally with a single command:

```bash
docker compose up --build
```

This provides:
- fast and simple local setup
- consistent environments across machines
- reduced configuration overhead

### Production Runtime

In production, all services run inside Docker containers managed by Docker Compose.  
When a new backend image is published via the CI/CD pipeline, **Watchtower automatically updates the running service**, ensuring the server stays in sync with the latest validated version.

## ⚙️ Configuration

Configuration is managed using **Spring Boot application properties** and **environment variables**, keeping sensitive data out of the codebase.

### Application Properties

Base configuration is defined in:

- `src/main/resources/application.properties`

Runtime-specific values (database connection, JWT secret, ports) are provided externally via environment variables.

### Environment Variables (Docker Compose)

When running with Docker Compose, the backend container receives configuration through environment variables, including:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `JWT_SECRET`

The Docker image reference is parameterized using:

- `DOCKERHUB_USERNAME` → `${DOCKERHUB_USERNAME}/phonebook:latest`

### CI/CD Secrets (GitHub)

Sensitive values used during the CI/CD process are stored securely using **GitHub Secrets**.

These secrets are injected into the GitHub Actions workflow at runtime and are used for:
- Docker registry authentication
- passing environment variables to the build and deployment steps
- keeping credentials out of the repository

### Logging Configuration

Logging is configured using:

- `src/main/resources/logback-spring.xml`

Application logs are mounted to the host machine via Docker volumes:
- `./logs:/app/logs`

## 🧪 Testing & Quality

The project includes **automated tests** to verify core backend behavior and ensure application stability during development and delivery.

### Automated Testing

Testing is implemented using the Spring Boot testing framework and is executed automatically as part of the CI pipeline.

- tests are run using `mvn test`
- failures stop the CI pipeline
- only validated changes are promoted further

This ensures that backend logic remains stable as the codebase evolves.

### Code Quality Practices

To maintain code quality, the project applies several backend best practices:

- clear separation between controllers, services, and repositories
- usage of DTOs to avoid exposing persistence models
- validation at the API boundary
- centralized exception handling for predictable error responses
- consistent logging configuration

Testing and quality checks are integrated naturally into the backend development workflow, helping prevent regressions and maintain long-term maintainability.

## 🌍 Live Deployment

The application is **deployed and running in a live environment**, making it accessible for real usage and testing.

The live deployment exists to demonstrate:
- a working backend service
- real request handling
- automated updates from the CI/CD pipeline

### Deployment Overview

- **Environment:** Production
- **Runtime:** Docker containers
- **Orchestration:** Docker Compose
- **Update mechanism:** Automatic container updates via Watchtower

The backend service runs continuously on the server and is updated automatically whenever a new Docker image is published.

### Deployment Behavior

- incoming requests are handled by the running backend container
- the frontend communicates with the backend over internal networking
- database data is persisted using Docker volumes
- service updates do not require manual redeployment

This setup demonstrates how a **Java backend application** can be deployed and maintained in a live environment with minimal manual intervention.

## 🗺️ Roadmap

Potential improvements planned for future iterations:

- Add refresh token support to improve authentication flow
- Increase test coverage for service and security layers
- Extend role-based authorization with finer-grained permissions

## 🧾 Summary

This project represents my approach to **Java backend development** with an emphasis on **clean architecture, security, testing, and maintainability**.

Rather than focusing on complex business features, the project demonstrates how a backend service can be:
- structured using proven architectural patterns
- secured with industry-standard authentication mechanisms
- tested and validated through automated workflows
- deployed and maintained in a real, running environment

The codebase reflects practical decisions commonly made in production backend systems, while the supporting tooling (CI/CD, Docker, and automation) is used to enhance reliability without overshadowing backend development itself.

Overall, this repository serves as a **backend-focused portfolio project** that highlights both technical foundations and real-world development practices.

## 👤 Author

**Bekzod Asanov**  
Java Backend Developer

- GitHub: https://github.com/asanov-b
- LinkedIn: https://www.linkedin.com/in/bekzod-asanov
