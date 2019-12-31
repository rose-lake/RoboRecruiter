package com.roselake.jbc;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Link {
    //*******
    // ID
    //*******
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //*********
    // OBJECTS
    //*********
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;

    @OneToOne(mappedBy = "link", fetch = FetchType.LAZY)
    private Interview interview;

    //********
    // FIELDS
    //********
    private LocalDate dateApplied;

    private String status;

    private String name;

    //**************
    // CONSTRUCTORS
    //**************
    public Link() {
    }

    // fully overloaded
    public Link(User user, Resume resume, Job job, Interview interview, LocalDate dateApplied, String status, String name) {
        this.user = user;
        this.resume = resume;
        this.job = job;
        this.interview = interview;
        this.dateApplied = dateApplied;
        this.status = status;
        this.name = name;
    }

    // no @Entity OBJECTS
    public Link(LocalDate dateApplied, String status, String name) {
        this.dateApplied = dateApplied;
        this.status = status;
        this.name = name;
    }

    // CUSTOM for DATA LOADER :: everything EXCEPT INTERVIEW !
    public Link(User user, Resume resume, Job job, LocalDate dateApplied, String status, String name) {
        this.user = user;
        this.resume = resume;
        this.job = job;
        this.dateApplied = dateApplied;
        this.status = status;
        this.name = name;
    }

    // CUSTOM constructor for creating the Application object BEFORE sending it into the FORM
    public Link(User user, Job job, LocalDate dateApplied) {
        this.user = user;
        this.job = job;
        this.dateApplied = dateApplied;
    }

    //*****************
    // GETTER / SETTER
    //*****************
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDateApplied() {
        return dateApplied;
    }

    public void setDateApplied(LocalDate dateApplied) {
        this.dateApplied = dateApplied;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //*****************
    // OBJECT METHODS
    //*****************
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Resume getResume() { return resume; }

    public void setResume(Resume resume) { this.resume = resume; }

    public Job getJob() { return job; }

    public void setJob(Job job) { this.job = job; }

    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }

}
