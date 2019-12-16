package com.example.demo;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

@Entity
public class Interview {

    //*******
    // ID
    //*******
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //*********
    // OBJECT
    //*********
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "link_id")
    private Link link;

    //********
    // FIELDS
    //********
    private LocalDate dateScheduled;

    private LocalTime timeWindowStart;

    private LocalTime timeWindowEnd;

    private static final ArrayList<String> behavioralQuestions= new ArrayList<>(Arrays.asList(
            "Describe a situation in which you met a major obstacle in order to complete a project. How did you deal with it? What steps did you take?",
            "Tell me about a time you had to work on several projects at once. How did you handle this?",
            "Describe a time when you made a suggestion to improve something on the project that you were working on...",
            "Give an example of a time when you didn’t agree with other programmer. Did you stand up for something that you believed was right?",
            "Tell me about when you had to deal with conflict within your team. How was the conflict solved? How did you handle that? How would you deal with it now?"));


    //**************
    // CONSTRUCTORS
    //**************
    public Interview() {}

    // CUSTOM constructor for making new interview and hooking it to its Link object
    public Interview(Link link) {
        this.link = link;
    }

    // NO LINK
    public Interview(LocalDate dateScheduled, LocalTime timeWindowStart, LocalTime timeWindowEnd) {
        this.dateScheduled = dateScheduled;
        this.timeWindowStart = timeWindowStart;
        this.timeWindowEnd = timeWindowEnd;
    }

    // fully overloaded
    public Interview(Link link, LocalDate dateScheduled, LocalTime timeWindowStart, LocalTime timeWindowEnd) {
        this.link = link;
        this.dateScheduled = dateScheduled;
        this.timeWindowStart = timeWindowStart;
        this.timeWindowEnd = timeWindowEnd;
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

    public LocalDate getDateScheduled() {
        return dateScheduled;
    }

    public void setDateScheduled(LocalDate dateScheduled) {
        this.dateScheduled = dateScheduled;
    }

    public LocalTime getTimeWindowStart() {
        return timeWindowStart;
    }

    public void setTimeWindowStart(LocalTime timeWindowStart) {
        this.timeWindowStart = timeWindowStart;
    }

    public LocalTime getTimeWindowEnd() {
        return timeWindowEnd;
    }

    public void setTimeWindowEnd(LocalTime timeWindowEnd) {
        this.timeWindowEnd = timeWindowEnd;
    }

    public static ArrayList<String> getBehavioralQuestions() {
        return behavioralQuestions;
    }

    //*****************
    // OBJECT METHOD
    //*****************
    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

}
