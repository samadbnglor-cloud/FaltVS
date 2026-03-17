package com.interviewscheduling.entity;

import java.util.List;

import org.hibernate.annotations.Index;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Interviewer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Index(name = "idx_interviewer_name")
    private String name;

    private String email;

    private String skills; // Comma-separated or something

    @OneToMany(mappedBy = "interviewer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Interview> interviews;

    // Constructors, getters, setters

    public Interviewer() {}

    public Interviewer(String name, String email, String skills) {
        this.name = name;
        this.email = email;
        this.skills = skills;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public List<Interview> getInterviews() {
        return interviews;
    }

    public void setInterviews(List<Interview> interviews) {
        this.interviews = interviews;
    }
}