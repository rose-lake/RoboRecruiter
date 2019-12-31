package com.roselake.jbc;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Role {
    //*******
    // ID
    //*******
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //*********
    // OBJECTS
    //*********
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Collection<User> users;

    //********
    // FIELDS
    //********
    @Column(unique=true)
    private String name;

    //**************
    // CONSTRUCTORS
    //**************
    public Role() {
    }

    public Role(String name) {
        this.name = name;
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

    //*****************
    // OBJECT METHOD
    //*****************
    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

}
