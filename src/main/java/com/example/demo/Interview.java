package com.example.demo;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

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


    //**************
    // CONSTRUCTORS
    //**************
    public Interview() {}

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
