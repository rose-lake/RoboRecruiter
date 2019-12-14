package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class HomeControllerRoot {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    LinkRepository linkRepository;
    @Autowired
    InterviewRepository interviewRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ResumeRepository resumeRepository;

    @RequestMapping("/")
    public String index(Model model) {

        Iterable<Link> links = linkRepository.findAll();
        ArrayList<Link> mylinks = new ArrayList<Link>();
        for (Link link : links) {
            mylinks.add(link);
        }
        LocalDate dateToday = LocalDate.now();
        LocalTime timeNow = LocalTime.now();

//        System.out.println("\n\n**** top of index method ****\n");
//        System.out.println("links fetched from repository (there should be NONE)... ");
//        if (links == null) {
//            System.out.println("links was null");
//        } else {
//            System.out.println("links not null, links = " + links.toString());
//            int counter = 1;
//            for (Link link : links) {
//                System.out.println("inside TEST for loop!");
//                System.out.println("\tlink #" + counter + " = " + link.toString());
//            }
//        }

        ArrayList<Link> takeInterviewLinks = new ArrayList<>();
        for (Link link : links) {

            if (link.getStatus().equalsIgnoreCase("Accepted")) {

                // interview object only gets created when you schedule
                // set status ACCEPTED --> DID NOT SCHEDULE
                if (dateToday.isAfter(link.getDateApplied().plusDays(14))) {
                    link.setStatus("Did Not Schedule");
                    linkRepository.save(link);
                }

            } else if (link.getStatus().equalsIgnoreCase("Interview Scheduled")) {

                Interview interview = link.getInterview();

                // if today is the day!
                if (dateToday.equals(interview.getDateScheduled())) {

                    // set status INTERVIEW SCHEDULED --> TAKE INTERVIEW
                    if (timeNow.isBefore(interview.getTimeWindowEnd())
                            || timeNow.equals(interview.getTimeWindowEnd())) {

                        if (timeNow.isAfter(interview.getTimeWindowStart())
                                || timeNow.equals(interview.getTimeWindowStart())) {
                            link.setStatus("Take Interview");
                            linkRepository.save(link);
                        }
                    }
                    // set status INTERVIEW SCHEDULED --> MISSED INTERVIEW
                    else {
                        link.setStatus("Missed Interview");
                        linkRepository.save(link);
                    }

                }
                // or, if we are AFTER the day!
                else if (dateToday.isAfter(interview.getDateScheduled())) {
                    // set status INTERVIEW SCHEDULED --> MISSED INTERVIEW
                    link.setStatus("Missed Interview");
                    linkRepository.save(link);
                }
            }

            // at the end of the for loop ::
            // load up all the interviews which are currently up for being taken!
            if (link.getStatus().equalsIgnoreCase("Take Interview")) {
                takeInterviewLinks.add(link);
            }

        }

//        System.out.println("bottom of index method. adding links to model. links = " + links.toString());
        model.addAttribute("links", mylinks);
        model.addAttribute("takeInterviewLinks", takeInterviewLinks);
        model.addAttribute("user", userService.getAuthenticatedUser());
        return "index";
    }

}
