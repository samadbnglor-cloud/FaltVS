# Interview Scheduling System

A microservice-based system for managing interview schedules, built with Spring Boot 3.5, Java 21, JPA, Hibernate, Liquibase, and H2 database.

## Features

- Schedule interviews between candidates and interviewers
- Submit feedback for completed interviews (automatically sets interview status to COMPLETED)
- Update interview status (cancel/reschedule) with conflict checking
- Search and list interviews with pagination and filtering by candidate name or interviewer name
- Email notifications for interview scheduling, updates, and feedback submission

## Technologies

- Java 21
- Spring Boot 3.5
- Spring Data JPA
- Hibernate
- Liquibase
- H2 Database
- Maven

## Project Structure

- `entity/`: JPA entities (Candidate, Interviewer, Interview, Feedback)
- `repository/`: JPA repositories
- `service/`: Business logic services
- `controller/`: REST controllers
- `dto/`: Data transfer objects
- `exception/`: Custom exceptions and global exception handler
- `config/`: Configuration classes (if any)

## API Endpoints

### Schedule Interview
- **POST** `/api/interviews/schedule`
- Request Body: `ScheduleInterviewRequest`

**Sample Request**
```json
{
  "candidateId": 1,
  "interviewerId": 1,
  "scheduledTime": "2026-03-20T10:00:00"
}
```

**Sample Response (201)**
```json
{
  "id": 5,
  "candidateName": "John Doe",
  "interviewerName": "Alice Brown",
  "scheduledTime": "2026-03-20T10:00:00",
  "status": "SCHEDULED",
  "feedback": null
}
```

### Submit Feedback
- **POST** `/api/interviews/feedback`
- Request Body: `SubmitFeedbackRequest`

> Feedback can only be submitted for interviews that are in `SCHEDULED` status. Submitting feedback automatically sets the interview status to `COMPLETED`. If feedback already exists, it will be updated.

**Sample Request**
```json
{
  "interviewId": 2,
  "rating": 4,
  "status": "SUBMITTED",
  "comments": "Great session"
}
```

**Sample Response (201)**
```json
{
  "status": "SUBMITTED",
  "comments": "Great session"
}
```

### Update Interview
- **PUT** `/api/interviews`
- Request Body: `UpdateInterviewRequest`

> Allows updating interview status and rescheduling. Cannot update completed interviews. If rescheduling, checks for interviewer conflicts.

**Sample Request**
```json
{
  "id": 5,
  "status": "CANCELLED",
  "scheduledTime": null
}
```

**Sample Response (200)**
```json
{
  "id": 5,
  "candidateName": "John Doe",
  "interviewerName": "Alice Brown",
  "scheduledTime": "2026-03-20T10:00:00",
  "status": "CANCELLED",
  "feedback": null
}
```

### Get Interviews
- **GET** `/api/interviews`
- Query parameters (optional):
  - `candidateName` (partial match)
  - `interviewerName` (partial match)
  - `status` (InterviewStatus: SCHEDULED, CANCELLED, COMPLETED)
  - `scheduledDate` (date in yyyy-MM-dd format)

**Sample Request**
`GET /api/interviews?status=SCHEDULED&scheduledDate=2026-03-20`

**Sample Response**
```json
[
  {
    "id": 1,
    "candidateName": "John Doe",
    "interviewerName": "Alice Brown",
    "scheduledTime": "2026-03-20T10:00:00",
    "status": "SCHEDULED",
    "feedback": null
  }
]
```
      "scheduledTime": "2026-03-20T10:00:00",
      "status": "SCHEDULED",
      "feedback": null
    }
  ],
  "pageable": { ... },
  "totalPages": 1,
  "totalElements": 1,
  "last": true,
  "size": 10,
  "number": 0,
  "sort": { ... },
  "numberOfElements": 1,
  "first": true,
  "empty": false
}
```

## Running the Application

1. Ensure Java 21 and Maven are installed.
2. Run `mvn spring-boot:run` to start the application.
3. The application will run on `http://localhost:8081`.
4. H2 console is available at `http://localhost:8081/h2-console`.

## Database

- Uses H2 in-memory database.
- Schema managed by Liquibase.
- JPA ddl-auto set to validate.

## Design Principles

- Follows SOLID principles.
- Modular architecture.
- Enterprise-grade quality.