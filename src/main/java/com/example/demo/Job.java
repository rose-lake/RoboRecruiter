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

    private String title;
    private String description;
    private String postedDate;
    private ArrayList<String> keywordList;
    private ArrayList<String> technicalQuestions;


    public Job(String title, String description, String postedDate, ArrayList<String> keywordList, ArrayList<String> technicalQuestions) {
        this.title = title;
        this.description = description;
        this.postedDate = postedDate;
        this.keywordList = keywordList;
        this.technicalQuestions = technicalQuestions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public ArrayList<String> getKeywordList() {
        return keywordList;
    }

    public void setKeywordList(ArrayList<String> keywordList) {
        this.keywordList = keywordList;
    }

    public ArrayList<String> getTechnicalQuestions() {
        return technicalQuestions;
    }

    public void setTechnicalQuestions(ArrayList<String> technicalQuestions) {
        this.technicalQuestions = technicalQuestions;
    }
}
