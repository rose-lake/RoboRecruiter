package com.example.demo;

import javax.persistence.*;
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

    private Date dateApplied;

    @OneToOne(mappedBy = "application", fetch=FetchType.LAZY)
    private Interview interview;

    private String status;

    public Application() {
    }

    public Application(User user, long resumeId, long jobId, Date dateApplied, Interview interview, String status) {
        this.user = user;
        this.resumeId = resumeId;
        this.jobId = jobId;
        this.dateApplied = dateApplied;
        this.interview = interview;
        this.status = status;
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

    public Date getDateApplied() {
        return dateApplied;
    }

    public void setDateApplied(Date dateApplied) {
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
