# Interview Scheduling System

A microservice-based system for managing interview schedules, built with Spring Boot 3.5, Java 21, JPA, Hibernate, Liquibase, and H2 database.

## Features

- Schedule interviews between candidates and interviewers
- Submit feedback for completed interviews
- Search and list interviews with pagination and filtering by candidate name or interviewer name

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
- Request Body: `ScheduleInterviewRequest` (candidateId, interviewerId, scheduledTime)

### Submit Feedback
- **POST** `/api/interviews/feedback`
- Request Body: `SubmitFeedbackRequest` (interviewId, rating, comments)

### Get Interviews
- **GET** `/api/interviews?candidateName={name}&interviewerName={name}&page={page}&size={size}`
- Returns paginated list of `InterviewResponse`

## Running the Application

1. Ensure Java 21 and Maven are installed.
2. Run `mvn spring-boot:run` to start the application.
3. The application will run on `http://localhost:8080`.
4. H2 console available at `http://localhost:8080/h2-console`.

## Database

- Uses H2 in-memory database.
- Schema managed by Liquibase.
- JPA ddl-auto set to validate.

## Design Principles

- Follows SOLID principles.
- Modular architecture.
- Enterprise-grade quality.