# Interview Scheduling System

A Spring Boot microservice that manages candidates, interviewers, interviews, and feedback.

## Key Features

- Schedule interviews between candidates and interviewers
- Submit feedback for completed interviews
- Search interviews with pagination and filter by candidate name or interviewer name
- OpenAPI/Swagger UI for interactive API exploration

## Tech Stack

- Java 21
- Spring Boot 3.5
- Spring Data JPA + Hibernate
- Liquibase for database migrations
- H2 database (file-based for persistence)
- SpringDoc OpenAPI (Swagger UI)

## Running the Application

1. Ensure Java 21 is installed.
2. From the project root, run:
   ```bash
   mvn spring-boot:run
   ```
3. Application will start on `http://localhost:8080`.

## API Endpoints

### Schedule an Interview
- **POST** `/api/interviews/schedule`
- Request JSON:
  ```json
  {
    "candidateId": 1,
    "interviewerId": 1,
    "scheduledTime": "2026-03-20T10:00:00"
  }
  ```

### Submit Feedback
- **POST** `/api/interviews/feedback`
- Request JSON:
  ```json
  {
    "interviewId": 2,
    "rating": 4,
    "comments": "Good technical skills, needs improvement in communication."
  }
  ```

### Search Interviews
- **GET** `/api/interviews`
- Query params (optional):
  - `candidateName`
  - `interviewerName`
  - `page` (default `0`)
  - `size` (default `10`)

## Documentation

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Database

- Uses H2 file database located at `~/testdb`
- Schema and sample data are created via Liquibase changelogs

## Notes

If the application fails to start due to a locked database file, close any open H2 console sessions and restart the application. If needed, delete `~/testdb.mv.db` to reset the database.