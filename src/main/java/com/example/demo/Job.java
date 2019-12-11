package com.example.demo;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;

@Entity

public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    @Column(columnDefinition = "VARCHAR(1023)")
    private String description;

    private LocalDate postedDate;

    @Column(columnDefinition = "VARBINARY(1023)")
    private ArrayList<String> keywordList;

    @Column(columnDefinition = "VARBINARY(1023)")
    private ArrayList<String> technicalQuestions;


    public Job(String title, String description, LocalDate postedDate, ArrayList<String> keywordList, ArrayList<String> technicalQuestions) {
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

    public LocalDate getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(LocalDate postedDate) {
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
