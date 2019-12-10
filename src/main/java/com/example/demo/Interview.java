package com.example.demo;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "application_id")
    private Application application;

    private LocalDate dateScheduled;

    private LocalTime timeScheduled;

    public Interview() {}

    public Interview(Application application, LocalDate dateScheduled, LocalTime timeScheduled) {
        this.application = application;
        this.dateScheduled = dateScheduled;
        this.timeScheduled = timeScheduled;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public LocalDate getDateScheduled() {
        return dateScheduled;
    }

    public void setDateScheduled(LocalDate dateScheduled) {
        this.dateScheduled = dateScheduled;
    }

    public LocalTime getTimeScheduled() {
        return timeScheduled;
    }

    public void setTimeScheduled(LocalTime timeScheduled) {
        this.timeScheduled = timeScheduled;
    }
}
