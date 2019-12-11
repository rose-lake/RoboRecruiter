package com.example.demo;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    private long resumeId;

    private long jobId;

    private LocalDate dateApplied;

    @OneToOne(mappedBy = "application", fetch=FetchType.LAZY)
    private Interview interview;

    private String status;

    public Application() {
    }

    public Application(User user, long resumeId, long jobId, LocalDate dateApplied, Interview interview, String status) {
        this.user = user;
        this.resumeId = resumeId;
        this.jobId = jobId;
        this.dateApplied = dateApplied;
        this.interview = interview;
        this.status = status;
    }

    // constructor for creating the Application object BEFORE sending it into the FORM
    // already on the back end you know and set user, job, and dateApplied (to today)
    public Application(User user, long jobId, LocalDate dateApplied) {
        this.user = user;
        this.jobId = jobId;
        this.dateApplied = dateApplied;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getResumeId() {
        return resumeId;
    }

    public void setResumeId(long resumeId) {
        this.resumeId = resumeId;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public LocalDate getDateApplied() {
        return dateApplied;
    }

    public void setDateApplied(LocalDate dateApplied) {
        this.dateApplied = dateApplied;
    }

    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
