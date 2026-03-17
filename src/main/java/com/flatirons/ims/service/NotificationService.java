package com.flatirons.ims.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService implements NotificationPort {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
    private final JavaMailSender mailSender;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendNotification(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        } catch (Exception e) {
            // Log the error, but don't fail the operation
            log.error("Failed to send email to {}: {}", to, e.getMessage());
        }
    }

    @Async
    public void notifyInterviewScheduled(String candidateEmail, String interviewerEmail, String candidateName, String interviewerName, String scheduledTime) {
        String subject = "Interview Scheduled";
        String candidateBody = String.format("Dear %s,\n\nYour interview with %s has been scheduled for %s.\n\nBest regards,\nInterview Scheduling System",
                candidateName, interviewerName, scheduledTime);
        String interviewerBody = String.format("Dear %s,\n\nYou have an interview scheduled with %s at %s.\n\nBest regards,\nInterview Scheduling System",
                interviewerName, candidateName, scheduledTime);

        sendNotification(candidateEmail, subject, candidateBody);
        sendNotification(interviewerEmail, subject, interviewerBody);
    }

    @Async
    public void notifyInterviewUpdated(String candidateEmail, String interviewerEmail, String candidateName, String interviewerName, String details) {
        String subject = "Interview Updated";
        String body = String.format("Dear Participant,\n\nThe interview between %s and %s has been updated: %s\n\nBest regards,\nInterview Scheduling System",
                candidateName, interviewerName, details);

        sendNotification(candidateEmail, subject, body);
        sendNotification(interviewerEmail, subject, body);
    }

    @Async
    public void notifyFeedbackSubmitted(String interviewerEmail, String interviewerName, String candidateName, String feedback) {
        String subject = "Feedback Submitted";
        String body = String.format("Dear %s,\n\nFeedback has been submitted for your interview with %s: %s\n\nBest regards,\nInterview Scheduling System",
                interviewerName, candidateName, feedback);

        sendNotification(interviewerEmail, subject, body);
    }
}