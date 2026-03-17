package com.flatirons.ims.constants;

public class AppConstants {
        
        public static final String API_BASE = "/api/interviews";
        public static final String SCHEDULE = "/schedule";
        public static final String UPDATE = "/update";
        public static final String FEEDBACK = "/feedback";
        public static final String LIST = "/list";
      
    public static final String INTERVIEW_NOT_FOUND = "Interview not found";
    public static final String CANDIDATE_NOT_FOUND = "Candidate not found";
    public static final String INTERVIEWER_NOT_FOUND = "Interviewer not found";
    public static final String FEEDBACK_ONLY_SCHEDULED = "Feedback can only be submitted for scheduled interviews";
    public static final String INTERVIEW_CONFLICT = "Interviewer has a conflicting interview at this time";
    public static final String CANNOT_UPDATE_COMPLETED = "Cannot update completed interviews";
    public static final String CANNOT_SCHEDULE_PAST = "Cannot schedule interview in the past";
    public static final String COMPLETED_ONLY_VIA_FEEDBACK = "Interview can only be completed by submitting feedback";
    
}
