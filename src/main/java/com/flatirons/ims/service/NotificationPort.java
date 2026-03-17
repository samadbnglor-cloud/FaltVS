package com.flatirons.ims.service;

public interface NotificationPort {
    void notifyInterviewScheduled(String candidateEmail, String interviewerEmail, String candidateName, String interviewerName, String scheduledTime);
    void notifyInterviewUpdated(String candidateEmail, String interviewerEmail, String candidateName, String interviewerName, String details);
    void notifyFeedbackSubmitted(String interviewerEmail, String interviewerName, String candidateName, String feedback);
}
