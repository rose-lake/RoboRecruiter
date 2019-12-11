package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class HomeControllerInterview {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    InterviewRepository interviewRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ResumeRepository resumeRepository;



}
