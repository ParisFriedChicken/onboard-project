# Onboard - Board Game Events Platform API

A backend platform designed to manage board game events and participation data, 
with a focus on reliability, data quality, and future AI enablement.

---

## Product Context

This platform was designed to support the organization of board game events between players.
Beyond core operational needs, its main goal is to provide reliable participation data
to support product and operational decision-making.

---

## Users & Use Cases

- Product teams use the platform to analyze participation patterns and assess cancellation risks.
- Operations teams rely on it to monitor event reliability and identify at-risk games.
- The platform is intentionally structured to enable future data and AI use cases.

---

## Architecture Overview

- REST API (Java backend)
- PostgreSQL database
- Docker Compose for local, production-like setup
- OpenAPI specification for API contract

---

## Data Model

The core data model is intentionally simple:
- Players
- Games (events)
- Participations (linking players to games with a status)

This structure allows basic operational workflows while remaining suitable for analytics
and predictive use cases.

---

## Reliability & Operations

The API exposes HTTP error codes and sanitized client-facing messages.
Key business events are logged to support monitoring and future data analysis.
The application can be started locally using Docker Compose with minimal configuration,
reflecting a production-like environment.

---

## Getting started

##### Prerequisites
- Docker
- Docker Compose
- Git

##### Clone the repository
```
git clone https://github.com/ParisFriedChicken/onboard-project
cd onboard-project

```
##### Run locally
```
docker-compose up

```
The API will be available at:
- Simple landing page: [http://localhost:8080/onboard](http://localhost:8080/onboard)
- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## API Documentation

The API contract is documented using OpenAPI and available via Swagger UI
once the application is running.

---

## Data & AI Readiness

The platform is designed as a foundation for data-driven features.
Participation history and event metadata can be leveraged to build
simple predictive signals, such as estimating the likelihood of event success.

---

## Project structure

```
src/
 ├── main/java/com/sebdev/onboard
 │     ├── config/      		→ Configuration classes
 │     ├── controller/      → REST Controllers
 │     ├── dto/      			→ Data Transfer Objects
 │     ├── service/         → Business Logic
 │     ├── repository/      → JPA Data Access
 │     ├── model/           → JPA Entities
 │     └── OnboardApplication.java
 └── test/java/          → Unit and Integration Tests
```

---

## Technologies

- **Spring Boot 3.x**
- **Spring Data JPA**
- **Spring Security (JWT)**
- **PostgreSQL**
- **Maven**
- **JUnit 5 + MockMvc**
- **OpenAPI**
- **Docker Compose**

---

## Auteur

Project developped by **[Sébastien Lemaitre](https://github.com/ParisFriedChicken/onboard-project)**  
Contact : sebastien.lemaitre@gmail.com

---

## Licence

This project is under MIT license — See file [LICENSE](LICENSE) pour plus de détails.
