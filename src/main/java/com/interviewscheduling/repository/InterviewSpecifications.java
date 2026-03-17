package com.interviewscheduling.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.interviewscheduling.entity.Interview;
import com.interviewscheduling.entity.InterviewStatus;

public class InterviewSpecifications {

    public static Specification<Interview> hasCandidateName(String candidateName) {
        return (root, query, criteriaBuilder) -> {
            if (candidateName == null || candidateName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                criteriaBuilder.lower(root.get("candidate").get("name")),
                "%" + candidateName.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Interview> hasInterviewerName(String interviewerName) {
        return (root, query, criteriaBuilder) -> {
            if (interviewerName == null || interviewerName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                criteriaBuilder.lower(root.get("interviewer").get("name")),
                "%" + interviewerName.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Interview> hasStatus(InterviewStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<Interview> hasScheduledDate(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            if (date == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(
                criteriaBuilder.function("DATE", LocalDate.class, root.get("scheduledTime")),
                date
            );
        };
    }
}