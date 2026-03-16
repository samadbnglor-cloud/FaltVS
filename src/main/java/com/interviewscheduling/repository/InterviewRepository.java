package com.interviewscheduling.repository;

import com.interviewscheduling.entity.Interview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {

    @Query("SELECT i FROM Interview i JOIN i.candidate c JOIN i.interviewer iv WHERE " +
           "(:candidateName IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :candidateName, '%'))) AND " +
           "(:interviewerName IS NULL OR LOWER(iv.name) LIKE LOWER(CONCAT('%', :interviewerName, '%')))")
    Page<Interview> findByFilters(@Param("candidateName") String candidateName,
                                  @Param("interviewerName") String interviewerName,
                                  Pageable pageable);
}