package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity

public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String jobTitle;
    private String jobDescription;
    private String jobPostedDate;
    private ArrayList jobKeywordList;
    private ArrayList jobTechnicalQuestions;

    public Job(String jobTitle, String jobDescription, String jobPostedDate, ArrayList jobKeywordList, ArrayList jobTechnicalQuestions) {
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.jobPostedDate = jobPostedDate;
        this.jobKeywordList = jobKeywordList;
        this.jobTechnicalQuestions = jobTechnicalQuestions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobPostedDate() {
        return jobPostedDate;
    }

    public void setJobPostedDate(String jobPostedDate) {
        this.jobPostedDate = jobPostedDate;
    }

    public ArrayList getJobKeywordList() {
        return jobKeywordList;
    }

    public void setJobKeywordList(ArrayList jobKeywordList) {
        this.jobKeywordList = jobKeywordList;
    }

    public ArrayList getJobTechnicalQuestions() {
        return jobTechnicalQuestions;
    }

    public void setJobTechnicalQuestions(ArrayList jobTechnicalQuestions) {
        this.jobTechnicalQuestions = jobTechnicalQuestions;
    }
}
