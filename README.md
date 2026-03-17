# Interview Scheduling System

A modular REST API built with **Java 21** and **Spring Boot 3.5** for managing interview scheduling, rescheduling, cancellation, and feedback submission.

## Tech Stack

| Layer         | Technology                          |
|---------------|-------------------------------------|
| Language      | Java 21                             |
| Framework     | Spring Boot 3.5                     |
| Database      | H2 (file-based, persistent)         |
| ORM           | Spring Data JPA / Hibernate         |
| Migrations    | Liquibase                           |
| Validation    | Jakarta Bean Validation             |
| Async         | Virtual Threads (Project Loom)      |
| Build         | Maven                               |

## Project Structure

```
com.flatirons.ims
├── config/             # AsyncConfig (virtual threads)
├── constants/          # AppConstants (API paths, error messages)
├── controller/         # InterviewController, FeedbackController
├── dto/                # Request/Response DTOs (records + classes)
├── entity/             # JPA entities (Interview, Feedback, Candidate, Interviewer)
├── enums/              # InterviewStatus, FeedbackStatus
├── exception/          # GlobalExceptionHandler, ResourceNotFoundException
├── mapper/             # InterviewMapper, FeedbackMapper (@Component beans)
├── repository/         # JPA repositories, InterviewSpecifications
├── service/            # InterviewService, FeedbackService, NotificationService
└── validation/         # Custom @ValidEnum annotation + validator
```

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.9+

### Run

```bash
mvn spring-boot:run
```

The application starts on `http://localhost:8080/ims`.

## API Endpoints

Base path: `/ims/api/interviews`

### Schedule Interview

```
POST /ims/api/interviews/schedule
```

```json
{
  "candidateId": 1,
  "interviewerId": 1,
  "scheduledTime": "2026-04-01T10:00:00"
}
```

**Response** `201 Created`:
```json
{
  "id": 1,
  "candidateName": "John Doe",
  "interviewerName": "Jane Smith",
  "scheduledTime": "2026-04-01T10:00:00",
  "status": "SCHEDULED",
  "feedback": null
}
```

### Update Interview (Reschedule / Cancel)

```
PATCH /ims/api/interviews/schedule
```

**Cancel:**
```json
{
  "id": 1,
  "status": "CANCELLED"
}
```

**Reschedule:**
```json
{
  "id": 1,
  "status": "SCHEDULED",
  "scheduledTime": "2026-04-02T14:00:00"
}
```

> **Note:** Setting status to `COMPLETED` is not allowed via this endpoint. Interviews are completed only through feedback submission.

### Submit Feedback

```
POST /ims/api/interviews/feedback
```

```json
{
  "interviewId": 1,
  "rating": 4,
  "status": "HIRED",
  "comments": "Strong technical skills"
}
```

**Response** `201 Created`:
```json
{
  "status": "HIRED",
  "comments": "Strong technical skills"
}
```

Submitting feedback automatically transitions the interview from `SCHEDULED` to `COMPLETED`. If feedback already exists for the interview, it is updated.

**FeedbackStatus values:** `HIRED`, `REJECTED`, `ON_HOLD`, `FURTHER_DISCUSSION`

### List Interviews

```
GET /ims/api/interviews
```

**Query Parameters:**

| Parameter         | Type   | Description                          |
|-------------------|--------|--------------------------------------|
| `candidateName`   | String | Filter by candidate name (partial)   |
| `interviewerName` | String | Filter by interviewer name (partial) |
| `page`            | int    | Page number (0-based, default: 0)    |
| `size`            | int    | Page size (default: 20)              |
| `sort`            | String | Sort field (e.g., `scheduledTime,desc`) |

**Response** `200 OK`: Returns a JSON array of interviews.

```json
[
  {
    "id": 1,
    "candidateName": "John Doe",
    "interviewerName": "Jane Smith",
    "scheduledTime": "2026-04-01T10:00:00",
    "status": "SCHEDULED",
    "feedback": null
  }
]
```

## Business Rules

- Interviews cannot be scheduled in the past.
- An interviewer cannot have overlapping interviews (1-hour slots).
- Completed interviews cannot be updated, rescheduled, or cancelled.
- Interview completion happens exclusively through feedback submission.
- Feedback can only be submitted for interviews in `SCHEDULED` status.

## Enums

**InterviewStatus:** `SCHEDULED`, `COMPLETED`, `CANCELLED`

**FeedbackStatus:** `HIRED`, `REJECTED`, `ON_HOLD`, `FURTHER_DISCUSSION`

## Error Handling

| HTTP Status | Scenario                                                      |
|-------------|---------------------------------------------------------------|
| 400         | Validation errors, invalid enum, past scheduling, conflict    |
| 404         | Interview / Candidate / Interviewer not found                 |
| 409         | Invalid state transition (e.g., cancel a completed interview) |
| 500         | Unexpected server error                                       |

## Design Principles

- **Single Responsibility** — Separate controllers and services for interviews and feedback.
- **Rich Domain Model** — Status transition rules live in the `Interview` entity (`complete()`, `cancel()`, `reschedule()`, `attachFeedback()`).
- **Ports & Adapters** — Notification logic is behind a `NotificationPort` interface, decoupled from business services.
- **Constructor Injection** — All dependencies injected via constructor; no field injection.
- **DTO / Entity Separation** — Request/response DTOs are isolated from JPA entities. Mappers are Spring-managed `@Component` beans.
- **Database Migrations** — Schema managed by Liquibase; JPA runs in `validate` mode.

## Future Improvements

- Redis for distributed caching
- Kafka / Message Queue for scalable notifications
- Externalized error messages via `error.properties`
- API rate limiting and circuit breakers
- Docker containerization
- Health checks and monitoring