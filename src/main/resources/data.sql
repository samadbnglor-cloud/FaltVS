-- Sample data for testing
INSERT INTO candidate (name, email) VALUES ('John Doe', 'john.doe@example.com');
INSERT INTO candidate (name, email) VALUES ('Jane Smith', 'jane.smith@example.com');
INSERT INTO candidate (name, email) VALUES ('Bob Johnson', 'bob.johnson@example.com');

INSERT INTO interviewer (name, email, skills) VALUES ('Alice Brown', 'alice.brown@example.com', 'Java, Spring');
INSERT INTO interviewer (name, email, skills) VALUES ('Charlie Wilson', 'charlie.wilson@example.com', 'Python, Django');
INSERT INTO interviewer (name, email, skills) VALUES ('Diana Davis', 'diana.davis@example.com', 'JavaScript, React');

INSERT INTO interview (candidate_id, interviewer_id, scheduled_time, status) VALUES (1, 1, '2026-03-20 10:00:00', 'SCHEDULED');
INSERT INTO interview (candidate_id, interviewer_id, scheduled_time, status) VALUES (2, 2, '2026-03-21 14:00:00', 'COMPLETED');
INSERT INTO interview (candidate_id, interviewer_id, scheduled_time, status) VALUES (3, 3, '2026-03-22 16:00:00', 'CANCELLED');
INSERT INTO interview (candidate_id, interviewer_id, scheduled_time, status) VALUES (1, 3, '2026-03-23 11:00:00', 'SCHEDULED');

INSERT INTO feedback (interview_id, rating, comments) VALUES (2, 4, 'Good technical skills, needs improvement in communication.');