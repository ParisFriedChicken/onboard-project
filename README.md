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

- First start docker 

```
docker-compose up -d

```
The API will be available at:
- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

##### Test API

- At first use : run the /auth/sigup endpoint to create a user access (click on "Try it out")
- Run the /auth/login endpoint with the created user's login/password 
- Copy the token
- Paste it in the Authorize Section
- Run any endpoint

##### Test AI predictive signal

- Swagger link : [http://localhost:8000/docs](http://localhost:8000/docs)
- Try the prediction method and see the changing of participation rate of a game by altering its parameters

---

## Data & AI Readiness

The platform is designed as a foundation for data-driven features.
Participation history and event metadata can be leveraged to build
simple predictive signals, such as estimating the likelihood of event success.

---

## AI Use Case

We are facing a problem with the usage of the app : a lot of games have a small participation rate, which often provoke cancellation of those games.
To solve that problem, we need an insight on the participation rate, how it can be influenced by certain characteristics relative to these games.
This predictive signal will help internal teams to anticipate risk and take some actions to mitigate them.

##### How the prediction is used

This signal can be used by the product team, to help them design and prioritize future functionalities intended to reduce the risk of low game participation.
It can be used with virtual game characteristics (such as host history of not showing to the games he was supposed to participate..) or real games, before they occur, to predict its chance of success.
This signal does not alter automatically the user experience.

##### Limits

The data used for this model is synthetic and limited, so its accuracy is restricted. The feedback loop mechanism isn't implemented.
With real usage data, the model performance could be improved.

## Observability & Monitoring

### Logged events
The following events are logged:
- AI prediction request received
- AI prediction successfully returned
- AI service failure

---

## Project structure

```
src/
 ├── main/java/com/sebdev/onboard
 │     ├── config/      	→ Configuration classes
 │     ├── controller/      → REST Controllers
 │     ├── dto/      		→ Data Transfer Objects
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
- **Python**
- **Fast API**

---

## Auteur

Project developped by **[Sébastien Lemaitre](https://github.com/ParisFriedChicken/onboard-project)**  
Contact : sebastien.lemaitre@gmail.com

---

## Licence

This project is under MIT license — See file [LICENSE](LICENSE) pour plus de détails.
