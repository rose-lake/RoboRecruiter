package com.roselake.jbc;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity

public class Job {
    //*******
    // ID
    //*******
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //*********
    // OBJECT
    //*********
    @OneToMany(mappedBy = "job", fetch = FetchType.LAZY)
    private List<Link> links;

    //********
    // FIELDS
    //********
    private String title;

    private LocalDate postedDate;

    @Column(columnDefinition = "VARCHAR(1023)")
    private String description;

    @Column(columnDefinition = "VARBINARY(1023)")
    private ArrayList<String> keywordList;

    @Column(columnDefinition = "VARBINARY(1023)")
    private ArrayList<String> technicalQuestions;

    //**************
    // CONSTRUCTORS
    //**************
    public Job() {
    }

    // custom constructor for DATA LOADER (does not include LINK object)
    public Job(String title, String description, LocalDate postedDate, ArrayList<String> keywordList, ArrayList<String> technicalQuestions) {
        this.title = title;
        this.description = description;
        this.postedDate = postedDate;
        this.keywordList = keywordList;
        this.technicalQuestions = technicalQuestions;
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

    //*****************
    // OBJECT METHOD
    //*****************
    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public void addLink(Link link) {

        if (this.links == null) {
            this.links = new ArrayList<>();
        }
        this.links.add(link);

    }

}
