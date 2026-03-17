package com.flatirons.ims.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flatirons.ims.entity.Interview;
import com.flatirons.ims.enums.InterviewStatus;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long>, JpaSpecificationExecutor<Interview> {

    @Query("SELECT i FROM Interview i WHERE i.interviewer.id = :interviewerId " +
           "AND i.status != :cancelledStatus " +
           "AND ((i.scheduledTime <= :startTime AND i.scheduledTime + 1 HOUR > :startTime) " +
           "OR (i.scheduledTime < :endTime AND i.scheduledTime + 1 HOUR >= :endTime) " +
           "OR (i.scheduledTime >= :startTime AND i.scheduledTime < :endTime))")
    List<Interview> findConflictingInterviews(@Param("interviewerId") Long interviewerId,
                                             @Param("startTime") LocalDateTime startTime,
                                             @Param("endTime") LocalDateTime endTime,
                                             @Param("cancelledStatus") InterviewStatus cancelledStatus);
}