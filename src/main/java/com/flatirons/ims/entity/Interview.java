package com.flatirons.ims.entity;

import java.time.LocalDateTime;

import com.flatirons.ims.enums.InterviewStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interviewer_id")
    private Interviewer interviewer;

    private LocalDateTime scheduledTime;

    @Enumerated(EnumType.STRING)
    private InterviewStatus status;

    @OneToOne(mappedBy = "interview", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Feedback feedback;

    public Interview() {}

    public Interview(Candidate candidate, Interviewer interviewer, LocalDateTime scheduledTime, InterviewStatus status) {
        this.candidate = candidate;
        this.interviewer = interviewer;
        this.scheduledTime = scheduledTime;
        this.status = status;
    }

    // --- Domain methods ---

    public void complete() {
        if (this.status != InterviewStatus.SCHEDULED) {
            throw new IllegalStateException("Only scheduled interviews can be completed");
        }
        this.status = InterviewStatus.COMPLETED;
    }

    public void cancel() {
        if (this.status == InterviewStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel a completed interview");
        }
        this.status = InterviewStatus.CANCELLED;
    }

    public void reschedule(LocalDateTime newTime) {
        if (this.status == InterviewStatus.COMPLETED) {
            throw new IllegalStateException("Cannot reschedule a completed interview");
        }
        this.scheduledTime = newTime;
        this.status = InterviewStatus.SCHEDULED;
    }

    public boolean isCompleted() {
        return this.status == InterviewStatus.COMPLETED;
    }

    public boolean isScheduled() {
        return this.status == InterviewStatus.SCHEDULED;
    }

    public void attachFeedback(Feedback feedback) {
        this.complete();
        feedback.setInterview(this);
        this.feedback = feedback;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Interviewer getInterviewer() {
        return interviewer;
    }

    public void setInterviewer(Interviewer interviewer) {
        this.interviewer = interviewer;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public InterviewStatus getStatus() {
        return status;
    }

    public void setStatus(InterviewStatus status) {
        this.status = status;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }
}