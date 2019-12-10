package com.example.demo;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@Entity
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Size(min=3)
    private String name;

    @Column(columnDefinition = "VARCHAR(4096)")
    @Size(min=10)
    private String content;

    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;

    public Resume() {
    }

    public Resume(@Size(min = 3) String name, @Size(min = 10) String content, User user) {
        this.name = name;
        this.content = content;
        this.user = user;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
