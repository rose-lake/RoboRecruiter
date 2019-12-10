package com.example.demo;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
<<<<<<< HEAD

    private ArrayList<String> resumeKeywords;
=======
    private String name;
    private ArrayList resumeKeywords;
>>>>>>> dd37031cf5e69e63dceb954e92c2ad9a2d263316

    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    public User user;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<String> getResumeKeywords() {
        return resumeKeywords;
    }

    public void setResumeKeywords(ArrayList<String> resumeKeywords) {
        this.resumeKeywords = resumeKeywords;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
