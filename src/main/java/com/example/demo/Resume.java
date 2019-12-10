package com.example.demo;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private ArrayList resumeKeywords;

    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    public User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList getResumeKeywords() {
        return resumeKeywords;
    }

    public void setResumeKeywords(ArrayList resumeKeywords) {
        this.resumeKeywords = resumeKeywords;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
