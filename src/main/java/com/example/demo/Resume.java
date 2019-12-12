package com.example.demo;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Resume {
    //*******
    // ID
    //*******
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //*********
    // OBJECTS
    //*********
    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY)
    private List<Link> links;

    //********
    // FIELDS
    //********
    @Size(min=3)
    private String name;

    @Column(columnDefinition = "VARCHAR(4095)")
    @Size(min=10)
    private String content;

    //**************
    // CONSTRUCTORS
    //**************
    public Resume() {
    }

    public Resume(@Size(min = 3) String name, @Size(min = 10) String content) {
        this.name = name;
        this.content = content;
    }

    // CUSTOM for DATA LOADER
    public Resume(User user, @Size(min = 3) String name, @Size(min = 10) String content) {
        this.user = user;
        this.name = name;
        this.content = content;
    }

    // fully overloaded for DATA LOADER
    public Resume(User user, List<Link> links, @Size(min = 3) String name, @Size(min = 10) String content) {
        this.user = user;
        this.links = links;
        this.name = name;
        this.content = content;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    //*****************
    // OBJECT METHODS + custom object methods
    //*****************
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Link> getLinks() { return links; }

    public void setLinks(List<Link> links) { this.links = links; }

    public void addLink(Link link) { this.links.add(link); }

}
