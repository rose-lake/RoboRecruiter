package com.example.demo;


import org.hibernate.annotations.Fetch;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="User_Data")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Resume> resumes;

    @Column(name="email", nullable=false)
    private String email;

    @Column(name="password")
    private String password;

    @Column(name ="first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name="username")
    private String username;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name="user_id"),inverseJoinColumns = @JoinColumn(name="role_id"))
    private Collection<Role> roles;

    public User(){}

    public User(String email, String password, String firstName, String lastName, boolean enabled, String username){
        this.setEmail(email);
        this.setPassword(password);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEnabled(enabled);
        this.setUsername(username);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) { this.password = password; }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public List<Resume> getResumes() {
        return resumes;
    }

    public void setResumes(List<Resume> resumes) {
        this.resumes = resumes;
    }

    public void addResume(Resume resume) { this.resumes.add(resume); }

    public void deleteResumeById(long resumeId) {
        int deleteIndex = -1;
        boolean found = false;
        for (Resume r : resumes) {
            if(r.getId() == resumeId){
                deleteIndex = resumes.indexOf(r);
                found = true;
                System.out.println("found resume with id = " + resumeId + " at index = " + deleteIndex + " in user with name = " + firstName);
                break;
            }
        }
        if (found && deleteIndex > -1){
            System.out.println("DELETING resume from user");
            resumes.remove(deleteIndex);
        }
        else {
            System.out.println("DELETE FAILED :: did not delete resume with id " + resumeId + " from user with name = " + firstName);
        }
    }

}
